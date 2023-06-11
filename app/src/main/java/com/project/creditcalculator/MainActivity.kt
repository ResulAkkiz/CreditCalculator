package com.project.creditcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.getSystemService
import androidx.core.view.size
import com.project.creditcalculator.databinding.ActivityMainBinding
import com.project.creditcalculator.model.Lectures
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {
    private val lectures =
        arrayListOf<String>("Matematik", "Fizik", "Türkçe", "Biyoloji", "Kimya", "Algoritma")

    private var lectureList = arrayListOf<Lectures>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, lectures)

        binding.autoCompleteTextView.setAdapter(adapter)

        if (binding.lineerLayoutView.childCount == 0) {
            binding.calculateAvg.visibility = View.INVISIBLE
        } else {
            binding.calculateAvg.visibility = View.VISIBLE
        }

        binding.addButton.setOnClickListener {

            if (!binding.autoCompleteTextView.text.isNullOrEmpty()) {
                val inflater = LayoutInflater.from(this)
                val newLessonView = inflater.inflate(R.layout.new_lesson_layout, null)
                val lectureName = binding.autoCompleteTextView.text.toString()
                val lectureCredit = binding.creditSpinner.selectedItem.toString()
                val lectureGrade = binding.gradeSpinner.selectedItem.toString()

                val etNewLectureNameVal: AutoCompleteTextView =
                    newLessonView.findViewById(R.id.etNewLectureName)
                etNewLectureNameVal.setText(lectureName)

                etNewLectureNameVal.setAdapter(adapter)

                val spnNewLectureCreditVal: Spinner =
                    newLessonView.findViewById(R.id.spnNewLectureCredit)
                spnNewLectureCreditVal.setSelection(
                    getIndexSpinner(
                        spnNewLectureCreditVal, lectureCredit
                    )
                )

                val spnNewLectureGradeVal: Spinner =
                    newLessonView.findViewById(R.id.spnNewLectureGrade)
                spnNewLectureGradeVal.setSelection(
                    getIndexSpinner(
                        spnNewLectureGradeVal, lectureGrade
                    )
                )

                val removeButtonView: Button = newLessonView.findViewById(R.id.removeButton)

                removeButtonView.setOnClickListener {
                    binding.lineerLayoutView.removeView(newLessonView)

                    if (binding.lineerLayoutView.childCount == 0) {
                        binding.calculateAvg.visibility = View.INVISIBLE
                    } else {
                        binding.calculateAvg.visibility = View.VISIBLE
                    }
                }

                binding.lineerLayoutView.addView(newLessonView)

                binding.calculateAvg.visibility = View.VISIBLE
                reset()
            } else {
                FancyToast.makeText(this,"Lütfen ders adı giriniz.",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }


        }


    }

    fun calculateAvg(view: View) {

        var toplamNot = 0.0
        var toplamKredi = 0.0
        for (i in 0 until binding.lineerLayoutView.childCount) {
            val singleRow = binding.lineerLayoutView.getChildAt(i)
            val etNewLectureNameVal: AutoCompleteTextView =
                singleRow.findViewById(R.id.etNewLectureName)
            val spnNewLectureGradeVal: Spinner =
                singleRow.findViewById(R.id.spnNewLectureGrade)
            val spnNewLectureCreditVal: Spinner =
                singleRow.findViewById(R.id.spnNewLectureCredit)


            var currentLecture = Lectures(
                etNewLectureNameVal.text.toString(),
                (spnNewLectureCreditVal.selectedItemPosition + 1).toString(),
                spnNewLectureGradeVal.selectedItem.toString()
            )

            lectureList.add(currentLecture)

            for (lecture in lectureList) {
                toplamNot += convertGrade(lecture.lectureGrade)*lecture.lectureCredit.toDouble()
                toplamKredi +=lecture.lectureCredit.toDouble()
            }
            FancyToast.makeText(this,"Ortalama: "+toplamNot/toplamKredi,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

            lectureList.clear()
        }
    }

    private fun reset() {
        binding.autoCompleteTextView.setText("")
        binding.gradeSpinner.setSelection(0)
        binding.creditSpinner.setSelection(0)

    }

    private fun getIndexSpinner(spinner: Spinner, value: String): Int {
        var index = 0
        for (i in 0..spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                index = i
                break
            }
        }
        return index
    }

    fun convertGrade(str: String): Double {
        var deger = 0.0
        when (str) {
            "AA" -> deger = 4.0
            "BA" -> deger = 3.5
            "BB" -> deger = 3.0
            "BC" -> deger = 2.5
            "CC" -> deger = 2.0
            "DC" -> deger = 1.5
            "DD" -> deger = 1.0
            "FF" -> deger = 0.0

        }
        return deger
    }
}
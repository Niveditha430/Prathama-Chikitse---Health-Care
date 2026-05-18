package com.example.prathamachikitse.firstaid

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prathamachikitse.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class FirstAidActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var rvFirstAid: RecyclerView
    private val firstAidList = mutableListOf<FirstAidItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_aid)

        tts = TextToSpeech(this, this)
        rvFirstAid = findViewById(R.id.rvFirstAid)
        rvFirstAid.layoutManager = LinearLayoutManager(this)

        prepareData()
        rvFirstAid.adapter = FirstAidAdapter(firstAidList) { item ->
            showDetailDialog(item)
        }

        findViewById<FloatingActionButton>(R.id.fabVoice).setOnClickListener {
            val fullText = "First Aid Instructions. " + firstAidList.joinToString(". ") { "${it.title}: ${it.descriptionEnglish}" }
            speakText(fullText)
            Toast.makeText(this, "Reading all instructions...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDetailDialog(item: FirstAidItem) {
        val message = "English:\n${item.descriptionEnglish}\n\nಕನ್ನಡ (Kannada):\n${item.descriptionKannada}\n\nImmediate Contact: ${item.contactInfo}"
        
        AlertDialog.Builder(this)
            .setTitle(item.title)
            .setMessage(message)
            .setPositiveButton("Speak English") { _, _ ->
                speakText(item.descriptionEnglish)
            }
            .setNeutralButton("Speak Kannada") { _, _ ->
                speakText(item.descriptionKannada, Locale("kn", "IN"))
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun speakText(text: String, locale: Locale = Locale.US) {
        tts.language = locale
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun prepareData() {
        firstAidList.clear()
        
        firstAidList.add(FirstAidItem(
            "💔 Heart Attack / ಹೃದಯಾಘಾತ",
            "1. Call 108 immediately. 2. Help person sit on the floor in 'W' position. 3. Loosen tight clothing. 4. Give 300mg Aspirin if available.",
            "೧. ಕೂಡಲೇ ೧೦೮ ಗೆ ಕರೆ ಮಾಡಿ. ೨. ವ್ಯಕ್ತಿಯನ್ನು 'W' ಆಕಾರದಲ್ಲಿ ಕುಳಿತುಕೊಳ್ಳಲು ಸಹಾಯ ಮಾಡಿ. ೩. ಬಿಗಿಯಾದ ಬಟ್ಟೆಯನ್ನು ಸಡಿಲಗೊಳಿಸಿ. ೪. ಆಸ್ಪಿರಿನ್ ಲಭ್ಯವಿದ್ದರೆ ನೀಡಿ.",
            "Ambulance: 108, Emergency: 112",
            R.drawable.ic_first_aid
        ))

        firstAidList.add(FirstAidItem(
            "🐍 Snake Bite / ಹಾವಿನ ಕಡಿತ",
            "1. Stay calm. 2. Immobilize the bitten limb. 3. Keep bite level below heart. 4. Do NOT use a tourniquet or suck venom.",
            "೧. ಶಾಂತವಾಗಿರಿ. ೨. ಕಚ್ಚಿದ ಭಾಗವನ್ನು ಚಲಿಸದಂತೆ ನೋಡಿಕೊಳ್ಳಿ. ೩. ಕಚ್ಚಿದ ಭಾಗವನ್ನು ಹೃದಯದ ಮಟ್ಟಕ್ಕಿಂತ ಕೆಳಗೆ ಇರಿಸಿ. ೪. ವಿಷವನ್ನು ಹೀರಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ.",
            "Poison Control: 1800 425 1213",
            R.drawable.ic_first_aid
        ))

        firstAidList.add(FirstAidItem(
            "🧪 Poisoning / ವಿಷ ಸೇವನೆ",
            "1. Identify the substance. 2. Do NOT induce vomiting unless told. 3. If on skin, rinse with water. 4. Call emergency services immediately.",
            "೧. ಯಾವ ಪದಾರ್ಥ ಸೇವಿಸಿದ್ದಾರೆ ಎಂದು ಗುರುತಿಸಿ. ೨. ವಾಂತಿ ಮಾಡಿಸಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ. ೩. ಚರ್ಮದ ಮೇಲಿದ್ದರೆ ನೀರಿನಿಂದ ತೊಳೆಯಿರಿ. ೪. ಕೂಡಲೇ ಆಸ್ಪತ್ರೆಗೆ ಕರೆದೊಯ್ಯಿರಿ.",
            "Poison Control: 1800 425 1213 / 108",
            R.drawable.ic_first_aid
        ))

        firstAidList.add(FirstAidItem(
            "🔥 Burns / ಸುಟ್ಟ ಗಾಯಗಳು",
            "1. Cool with running water for 20 mins. 2. Remove jewelry/clothing near burn. 3. Cover with clean film.",
            "೧. ಹರಿಯುವ ತಣ್ಣೀರಿನಲ್ಲಿ ೨೦ ನಿಮಿಷ ಹಿಡಿಯಿರಿ. ೨. ಸುಟ್ಟ ಗಾಯದ ಹತ್ತಿರವಿರುವ ಒಡವೆ ಅಥವಾ ಬಟ್ಟೆಯನ್ನು ತೆಗೆಯಿರಿ. ೩. ಸ್ವಚ್ಛವಾದ ಬಟ್ಟೆಯಿಂದ ಮುಚ್ಚಿ.",
            "Emergency: 108",
            R.drawable.ic_first_aid
        ))
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }

    data class FirstAidItem(
        val title: String, 
        val descriptionEnglish: String, 
        val descriptionKannada: String,
        val contactInfo: String,
        val imageRes: Int
    )

    class FirstAidAdapter(private val items: List<FirstAidItem>, private val onClick: (FirstAidItem) -> Unit) : RecyclerView.Adapter<FirstAidAdapter.ViewHolder>() {
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val title: TextView = v.findViewById(R.id.tvFirstAidTitle)
            val desc: TextView = v.findViewById(R.id.tvFirstAidDesc)
            val img: ImageView = v.findViewById(R.id.ivFirstAid)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_first_aid, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.title
            holder.desc.text = item.descriptionEnglish
            holder.img.setImageResource(item.imageRes)
            holder.itemView.setOnClickListener { onClick(item) }
        }

        override fun getItemCount() = items.size
    }
}

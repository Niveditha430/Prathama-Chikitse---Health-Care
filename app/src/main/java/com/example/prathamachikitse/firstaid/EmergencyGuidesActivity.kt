package com.example.prathamachikitse.firstaid

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.example.prathamachikitse.databinding.ActivityEmergencyGuidesBinding
import java.util.*

class EmergencyGuidesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityEmergencyGuidesBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyGuidesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        tts = TextToSpeech(this, this)

        binding.btnHeartAttack.setOnClickListener { showDetailedGuide("Heart Attack / ಹೃದಯಾಘಾತ", Guides.HEART_ATTACK) }
        binding.btnSnakeBite.setOnClickListener { showDetailedGuide("Snake Bite / ಹಾವು ಕಡಿತ", Guides.SNAKE_BITE) }
        binding.btnFracture.setOnClickListener { showDetailedGuide("Fracture / ಮೂಳೆ ಮುರಿತ", Guides.FRACTURE) }
        binding.btnBurns.setOnClickListener { showDetailedGuide("Burns / ಸುಟ್ಟ ಗಾಯ", Guides.BURNS) }
        binding.btnChoking.setOnClickListener { showDetailedGuide("Choking / ಉಸಿರುಕಟ್ಟುವಿಕೆ", Guides.CHOKING) }
        binding.btnBleeding.setOnClickListener { showDetailedGuide("Bleeding / ರಕ್ತಸ್ರಾವ", Guides.BLEEDING) }
        binding.btnElectricShock.setOnClickListener { showDetailedGuide("Electric Shock / ವಿದ್ಯುತ್ ಆಘಾತ", Guides.ELECTRIC_SHOCK) }
        binding.btnPoisoning.setOnClickListener { showDetailedGuide("Poisoning / ವಿಷಪೂರಿತ", Guides.POISONING) }
    }

    private fun showDetailedGuide(title: String, guide: GuideContent) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_emergency_guide, null)
        val dialog = AlertDialog.Builder(this, R.style.Theme_PrathamaChikitse)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.tvGuideTitle).text = title
        dialogView.findViewById<TextView>(R.id.tvDosEn).text = guide.dosEn
        dialogView.findViewById<TextView>(R.id.tvDosKn).text = guide.dosKn
        dialogView.findViewById<TextView>(R.id.tvDontsEn).text = guide.dontsEn
        dialogView.findViewById<TextView>(R.id.tvDontsKn).text = guide.dontsKn

        dialogView.findViewById<Button>(R.id.btnSpeakEn).setOnClickListener {
            speak("DO's: " + guide.dosEn + " . DON'Ts: " + guide.dontsEn, Locale.US)
        }

        dialogView.findViewById<Button>(R.id.btnSpeakKn).setOnClickListener {
            speak("ಮಾಡಬೇಕಾದ್ದು: " + guide.dosKn + " . ಮಾಡಬಾರದ್ದು: " + guide.dontsKn, Locale("kn", "IN"))
        }

        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun speak(text: String, locale: Locale) {
        tts.language = locale
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
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

    data class GuideContent(val dosEn: String, val dontsEn: String, val dosKn: String, val dontsKn: String)

    object Guides {
        val HEART_ATTACK = GuideContent(
            "1. Call 108 immediately.\n2. Help the person sit down and stay calm.\n3. Loosen tight clothing.\n4. Ask if they take chest pain medicine like nitroglycerin.",
            "1. Do not leave the person alone.\n2. Do not let them walk or drive.\n3. Do not give them anything to eat or drink except prescribed medicine.",
            "1. ತಕ್ಷಣ 108 ಕ್ಕೆ ಕರೆ ಮಾಡಿ.\n2. ವ್ಯಕ್ತಿಯನ್ನು ಕುಳಿತುಕೊಳ್ಳಲು ಮತ್ತು ಶಾಂತವಾಗಿರಲು ಸಹಾಯ ಮಾಡಿ.\n3. ಬಿಗಿಯಾದ ಬಟ್ಟೆಯನ್ನು ಸಡಿಲಗೊಳಿಸಿ.\n4. ಅವರು ಎದೆನೋವು ಔಷಧಿಯನ್ನು ತೆಗೆದುಕೊಳ್ಳುತ್ತಾರೆಯೇ ಎಂದು ಕೇಳಿ.",
            "1. ವ್ಯಕ್ತಿಯನ್ನು ಒಬ್ಬಂಟಿಯಾಗಿ ಬಿಡಬೇಡಿ.\n2. ಅವರಿಗೆ ನಡೆಯಲು ಅಥವಾ ಚಾಲನೆ ಮಾಡಲು ಬಿಡಬೇಡಿ.\n3. ವೈದ್ಯರು ಸೂಚಿಸಿದ ಔಷಧಿ ಹೊರತುಪಡಿಸಿ ಬೇರೆ ಏನನ್ನೂ ತಿನ್ನಲು ಅಥವಾ ಕುಡಿಯಲು ನೀಡಬೇಡಿ."
        )
        val SNAKE_BITE = GuideContent(
            "1. Call 108.\n2. Keep the bitten area below the heart level.\n3. Keep the victim still.\n4. Remove jewelry or tight clothing before swelling starts.",
            "1. Do not cut the wound.\n2. Do not try to suck out the venom.\n3. Do not apply ice or a tourniquet.\n4. Do not let the victim walk.",
            "1. 108 ಕ್ಕೆ ಕರೆ ಮಾಡಿ.\n2. ಕಡಿತದ ಭಾಗವನ್ನು ಹೃದಯದ ಮಟ್ಟಕ್ಕಿಂತ ಕೆಳಗೆ ಇರಿಸಿ.\n3. ವ್ಯಕ್ತಿಯನ್ನು ಅಲುಗಾಡದಂತೆ ಇರಿಸಿ.\n4. ಊತ ಬರುವ ಮೊದಲು ಒಡವೆ ಅಥವಾ ಬಿಗಿಯಾದ ಬಟ್ಟೆಯನ್ನು ತೆಗೆಯಿರಿ.",
            "1. ಗಾಯವನ್ನು ಕತ್ತರಿಸಬೇಡಿ.\n2. ವಿಷವನ್ನು ಹೀರಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ.\n3. ಐಸ್ ಅಥವಾ ಟೂರ್ನಿಕೆಟ್ ಬಳಸಬೇಡಿ.\n4. ವ್ಯಕ್ತಿಯನ್ನು ನಡೆಯಲು ಬಿಡಬೇಡಿ."
        )
        val FRACTURE = GuideContent(
            "1. Stop any bleeding by applying pressure.\n2. Immobilize the injured area using a splint or sling.\n3. Apply ice packs to limit swelling.",
            "1. Do not try to realign or push back a bone.\n2. Do not move the person if you suspect a neck or back injury.",
            "1. ಒತ್ತಡ ಹಾಕುವ ಮೂಲಕ ರಕ್ತಸ್ರಾವ ನಿಲ್ಲಿಸಿ.\n2. ಗಾಯಗೊಂಡ ಭಾಗಕ್ಕೆ ಆಧಾರ ನೀಡಿ.\n3. ಊತ ಕಡಿಮೆ ಮಾಡಲು ಐಸ್ ಹಚ್ಚಿ.",
            "1. ಮೂಳೆಯನ್ನು ಸರಿಪಡಿಸಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ.\n2. ಬೆನ್ನು ಅಥವಾ ಕುತ್ತಿಗೆಗೆ ಗಾಯವಾಗಿದ್ದರೆ ವ್ಯಕ್ತಿಯನ್ನು ಅಲುಗಾಡಿಸಬೇಡಿ."
        )
        val BURNS = GuideContent(
            "1. Cool the burn with cool (not cold) running water for 20 mins.\n2. Remove rings or other tight items from the burned area.",
            "1. Do not use ice, butter, or ointments on the burn.\n2. Do not break blisters.\n3. Do not remove clothing stuck to the burn.",
            "1. ಸುಟ್ಟ ಗಾಯದ ಮೇಲೆ 20 ನಿಮಿಷ ತಣ್ಣೀರು ಹರಿಸಿರಿ.\n2. ಸುಟ್ಟ ಭಾಗದಿಂದ ಉಂಗುರ ಅಥವಾ ಬಿಗಿಯಾದ ವಸ್ತುಗಳನ್ನು ತೆಗೆಯಿರಿ.",
            "1. ಐಸ್, ಬೆಣ್ಣೆ ಅಥವಾ ಮುಲಾಮು ಹಚ್ಚಬೇಡಿ.\n2. ಗುಳ್ಳೆಗಳನ್ನು ಒಡೆಯಬೇಡಿ.\n3. ಅಂಟಿಕೊಂಡಿರುವ ಬಟ್ಟೆಯನ್ನು ತೆಗೆಯಬೇಡಿ."
        )
        val CHOKING = GuideContent(
            "1. Give 5 back blows between the shoulder blades.\n2. Give 5 abdominal thrusts (Heimlich maneuver).",
            "1. Do not try to reach into the mouth blindly.\n2. Do not give water until the airway is clear.",
            "1. ಬೆನ್ನಿನ ಮಧ್ಯಭಾಗದಲ್ಲಿ 5 ಬಾರಿ ತಟ್ಟಿ.\n2. ಹೊಟ್ಟೆಯ ಮೇಲೆ 5 ಬಾರಿ ಒತ್ತಡ ಹಾಕಿ (ಹೀಮ್ಲಿಚ್ ಕುಶಲತೆ).",
            "1. ಬಾಯಿಗೆ ಅಂಧವಾಗಿ ಕೈ ಹಾಕಬೇಡಿ.\n2. ಉಸಿರಾಟದ ಮಾರ್ಗ ಸರಿಯಾಗುವವರೆಗೆ ನೀರು ನೀಡಬೇಡಿ."
        )
        val BLEEDING = GuideContent(
            "1. Apply direct pressure with a clean cloth.\n2. Elevate the wounded area above the heart if possible.",
            "1. Do not remove the cloth if it becomes soaked; add more on top.\n2. Do not remove embedded objects; stabilize them.",
            "1. ಸ್ವಚ್ಛ ಬಟ್ಟೆಯಿಂದ ನೇರ ಒತ್ತಡ ಹಾಕಿ.\n2. ಸಾಧ್ಯವಾದರೆ ಗಾಯದ ಭಾಗವನ್ನು ಹೃದಯಕ್ಕಿಂತ ಎತ್ತರಕ್ಕೆ ಇರಿಸಿ.",
            "1. ಬಟ್ಟೆ ನೆನೆದರೆ ಅದನ್ನು ತೆಗೆಯಬೇಡಿ; ಅದರ ಮೇಲೆ ಇನ್ನೊಂದು ಬಟ್ಟೆ ಇರಿಸಿ.\n2. ಚುಚ್ಚಿಕೊಂಡ ವಸ್ತುಗಳನ್ನು ತೆಗೆಯಬೇಡಿ."
        )
        val ELECTRIC_SHOCK = GuideContent(
            "1. Turn off the power source if safe.\n2. Move the person using a wooden stick or dry rope.",
            "1. Do not touch the victim with bare hands if they are still in contact with electricity.",
            "1. ವಿದ್ಯುತ್ ಮೂಲವನ್ನು ಆಫ್ ಮಾಡಿ.\n2. ಮರದ ಕೋಲು ಅಥವಾ ಒಣ ಹಗ್ಗ ಬಳಸಿ ವ್ಯಕ್ತಿಯನ್ನು ಸರಿಸಿ.",
            "1. ವಿದ್ಯುತ್ ಸಂಪರ್ಕದಲ್ಲಿದ್ದರೆ ವ್ಯಕ್ತಿಯನ್ನು ಬರಿಗೈಯಿಂದ ಮುಟ್ಟಬೇಡಿ."
        )
        val POISONING = GuideContent(
            "1. Try to identify the substance.\n2. Call 108 or a poison control center immediately.",
            "1. Do not induce vomiting unless told by a doctor.\n2. Do not give anything by mouth if the person is unconscious.",
            "1. ವಿಷ ಏನೆಂದು ಗುರುತಿಸಲು ಪ್ರಯತ್ನಿಸಿ.\n2. ತಕ್ಷಣ 108 ಕ್ಕೆ ಕರೆ ಮಾಡಿ.",
            "1. ವೈದ್ಯರು ಹೇಳದ ಹೊರತು ವಾಂತಿ ಮಾಡಿಸಲು ಪ್ರಯತ್ನಿಸಬೇಡಿ.\n2. ವ್ಯಕ್ತಿ ಪ್ರಜ್ಞೆ ತಪ್ಪಿದ್ದರೆ ಬಾಯಿಯ ಮೂಲಕ ಏನನ್ನೂ ನೀಡಬೇಡಿ."
        )
    }
}

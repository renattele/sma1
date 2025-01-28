package com.renattele.sma1.presentation.fragment

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.renattele.sma1.R
import com.renattele.sma1.app
import com.renattele.sma1.data.ThemesRepository
import com.renattele.sma1.databinding.FragmentNotificationBinding
import com.renattele.sma1.domain.NotificationPriority
import com.renattele.sma1.domain.Theme
import com.renattele.sma1.presentation.activity.MainActivity
import com.renattele.sma1.presentation.adapter.ThemesAdapter
import com.renattele.sma1.utils.showSnackbar
import com.renattele.sma1.utils.showToast

private val EMPTY_IMAGE_RESOURCE = R.drawable.ic_launcher_background

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val themes = ThemesRepository.themes
    private var avatarUri: Uri? = null
        set(value) {
            field = value
            val deleteButtonVisibility = if (value == null) View.GONE else View.VISIBLE
            binding.avatarDeleteButton.visibility = deleteButtonVisibility

            if (value == null) {
                binding.avatarImage.setImageResource(EMPTY_IMAGE_RESOURCE)
            } else binding.avatarImage.setImageURI(value)
        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                avatarUri = uri
            }
        }

    private var isExpanded = false

    private val notificationManager by lazy {
        NotificationManagerCompat.from(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater)
        requestNotificationPermission()
        binding.apply {
            themesRecycler.adapter = ThemesAdapter(themes, ::selectTheme)
            themesRecycler.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            avatarImage.setOnClickListener {
                selectAvatar()
            }
            expandThemesButton.setOnClickListener {
                toggleThemes()
            }
            avatarDeleteButton.setOnClickListener {
                deleteAvatar()
            }
            val priorities = NotificationPriority.entries
            val prioritiesAdapter =
                ArrayAdapter(requireContext(), R.layout.autocomplete_list_item, priorities)
            prioritySpinner.adapter = prioritiesAdapter
            showNotificationButton.setOnClickListener {
                tryShowNotification()
            }
            clearThemeButton.setOnClickListener {
                clearTheme()
            }
            toggleThemes(expanded = false)
            avatarUri = null
        }
        return binding.root
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0
            )
        }
    }

    private fun selectAvatar() {
        if (avatarUri != null) {
            requireContext().showToast(getString(R.string.avatar_is_already_set))
        } else {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun selectTheme(position: Int) {
        setTheme(themes[position])
    }

    private fun toggleThemes(expanded: Boolean = !isExpanded) {
        val resource = if (expanded) R.drawable.baseline_keyboard_double_arrow_up_24
        else R.drawable.baseline_keyboard_double_arrow_down_24

        binding.expandThemesButton.setImageResource(resource)
        val visibility = if (expanded) View.VISIBLE else View.GONE
        binding.themesRecycler.visibility = visibility
        isExpanded = expanded
    }

    private fun deleteAvatar() {
        avatarUri = null
    }

    private fun tryShowNotification() {
        val title = binding.notificationHeaderEditTextLayout.editText?.text
        val body = binding.notificationBodyEditTextLayout.editText?.text
        val priorityText = binding.prioritySpinner.selectedItem
        val priority = priorityText as? NotificationPriority

        if (title.isNullOrBlank()) {
            binding.root.showSnackbar(getString(R.string.please_fill_notification_header))
            return
        }
        if (body.isNullOrBlank()) {
            binding.root.showSnackbar(getString(R.string.please_fill_notification_body))
            return
        }
        if (priority == null) {
            binding.root.showSnackbar(getString(R.string.please_fill_priority))
            return
        }
        showNotification(title = title.toString(), body = body.toString(), priority = priority)
    }

    private fun showNotification(title: String, body: String, priority: NotificationPriority) {
        val channelId = getString(R.string.test_notification_channel)
        val notificationChannel =
            NotificationChannelCompat.Builder(channelId, priority.androidPriority)
                .setName(channelId).build()
        notificationManager.createNotificationChannel(notificationChannel)
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra(MainActivity.FROM_NOTIFICATION_KEY, true)
        }
        val pendingIntent = PendingIntent.getActivity(
            requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(priority.androidPriority)
            .setContentIntent(pendingIntent)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(0, notification)
        }
    }

    private fun clearTheme() {
        setTheme(null)
    }

    private fun setTheme(theme: Theme?) {
        requireContext().app.theme = theme
        requireActivity().intent.removeExtra(MainActivity.FROM_NOTIFICATION_KEY)
        requireActivity().recreate()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
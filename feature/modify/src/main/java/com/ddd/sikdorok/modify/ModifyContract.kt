package com.ddd.sikdorok.modify

import android.net.Uri

class ModifyContract {
    data class State(
        val id: String = "",
        val image: Uri? = null,
        val date: String = "",
        val time: String = "",
        val icon: String = "",
        val tag: String = "",
        val isMainPost: Boolean = false,
        val memo: String = "",
        val imageUrl: String? = null,
        val imageRefresh: Boolean? = null
    )

    sealed class Event {
        object OnClickBackIcon : Event()

        object OnClickCameraFAB : Event()

        data class OnClickPostItem(val type: Type, val isGrant: Boolean) : Event() {
            enum class Type {
                CAMERA, ALBUM
            }
        }

        object OnClickImageForDefault : Event()

        data class OnClickDay(val code: String) : Event()

        data class OnClickIcon(val code: String) : Event()

        data class EditText(val text: String) : Event()

        data class OnClickMainPost(val isChecked: Boolean) : Event()

        data class OnFinishTimePicker(val time: String) : Event()

        data class OnFinishDatePicker(val time: String) : Event()

        data class InputMemo(val memo: String) : Event()

        object OnClickMore : Event()

        data class OnClickCheck(val isChecked: Boolean) : Event()

        object OnClickDelete : Event()

        object OnClickShare : Event()

        data class OnUpdateImage(val uri: Uri) : Event()

        data class OnClickDate(val date: String) : Event()

        data class OnClickTime(val time: String) : Event()

        data class OnSavedFeed(
            val file: ByteArray?,
            val tag: String?,
            val time: String,
            val memo: String,
            val icon: String,
            val isMainFeed: Boolean
        ) : Event()
    }

    sealed class SideEffect {
        object OnFinishCreate : SideEffect()

        object OnFinishModify : SideEffect()

        object OnFinishDelete : SideEffect()

        object OnFinish : SideEffect()

        object RequestPermission : SideEffect()

        object ShowPostDialog : SideEffect()

        object RequestCamera : SideEffect()

        object RequestAlbum : SideEffect()

        object NaviToCamera : SideEffect()

        object NaviToAlbum : SideEffect()

        object OpenMenu : SideEffect()

        data class ShowDatePicker(val date: String) : SideEffect()

        data class ShowTimePicker(val time: String) : SideEffect()

        data class Fail(val errorMsg: String) : SideEffect()
    }
}

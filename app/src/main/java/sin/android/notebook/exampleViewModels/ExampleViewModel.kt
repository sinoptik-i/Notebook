package sin.android.notebook.exampleViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sin.android.notebook.data.Note

data class ScreenStateVM(
    var seeAllNotes: Boolean = true,
    var selectedNote: Note = Note(0, "", "")
)

data class ScreenStateVM2(
    var seeAllNotes: Boolean = true,
    // var selectedNote: Note = Note(0, "", "")
)


class ExampleViewModel : ViewModel() {

    private val _count: MutableLiveData<Int> = MutableLiveData(3)
    val count: LiveData<Int> = _count

    fun releaseNewKittens() {
        viewModelScope.launch {
            delay(2000)
            _count.postValue(count.value?.plus(10))
        }
    }


    private val _screenStateVM: MutableLiveData<ScreenStateVM> =
        MutableLiveData(ScreenStateVM())

    val screenStateVM: LiveData<ScreenStateVM> = _screenStateVM


    private val _screenStateVM2: MutableLiveData<ScreenStateVM2> =
        MutableLiveData(ScreenStateVM2())

    val screenStateVM2: LiveData<ScreenStateVM2> = _screenStateVM2

    val screenState: MutableLiveData<ScreenStateVM2> = MutableLiveData(ScreenStateVM2())


    fun changeScreen() {
        screenState.value?.seeAllNotes = !screenState.value?.seeAllNotes!!
    }

}
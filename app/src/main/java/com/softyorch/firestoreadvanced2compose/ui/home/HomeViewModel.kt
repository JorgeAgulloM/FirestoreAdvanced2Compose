package com.softyorch.firestoreadvanced2compose.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softyorch.firestoreadvanced2compose.data.network.DatabaseRepository
import com.softyorch.firestoreadvanced2compose.domain.CanAccessToApp
import com.softyorch.firestoreadvanced2compose.domain.dto.TransactionDTO.Companion.prepareDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val db: DatabaseRepository,
    private val canAccessToApp: CanAccessToApp
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _showBlockDialog = MutableStateFlow<Boolean?>(null)
    val showBlockDialog: StateFlow<Boolean?> = _showBlockDialog

    init {
        initConfigApp()
        viewModelScope.launch {
            db.getTransactions().collect { transactions ->
                _uiState.update { state ->
                    state.copy(
                        transactions = transactions,
                        totalAmount = String.format("%.2f", transactions.sumOf { it.amount })
                    )
                }
            }
        }
    }

    fun onShowTransactionDialog() {
        _uiState.update { it.copy(showAddTransaction = true) }
    }

    fun onDismissTransactionDialog() {
        _uiState.update { it.copy(showAddTransaction = false) }
    }

    fun onAddNewTransaction(title: String, amount: String, date: Long?) {
        prepareDTO(title, amount, date)?.let { dto ->
            viewModelScope.launch {
                db.addTransaction(dto)
            }
        }
        onDismissTransactionDialog()
    }

    fun deleteTransaction(id: String) {
        db.deleteTransaction(id)
    }

    private fun initConfigApp() {
        val title = db.getAppInfo()
        Log.i("LOGTAG", "RemoteConfig -> title: $title")

        viewModelScope.launch {
            val canAccess = withContext(Dispatchers.IO) {
                canAccessToApp()
            }

            _showBlockDialog.value = !canAccess
        }
    }

}

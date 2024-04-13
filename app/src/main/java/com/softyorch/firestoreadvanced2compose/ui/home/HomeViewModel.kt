package com.softyorch.firestoreadvanced2compose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softyorch.firestoreadvanced2compose.data.network.DatabaseRepository
import com.softyorch.firestoreadvanced2compose.domain.dto.TransactionDTO.Companion.prepareDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val db: DatabaseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
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

}

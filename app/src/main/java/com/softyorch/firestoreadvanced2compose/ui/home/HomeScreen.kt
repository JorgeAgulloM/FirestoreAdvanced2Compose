package com.softyorch.firestoreadvanced2compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softyorch.firestoreadvanced2compose.R
import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel
import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel.Companion.amountFormatted
import com.softyorch.firestoreadvanced2compose.ui.theme.Gray
import com.softyorch.firestoreadvanced2compose.ui.theme.Purple40
import com.softyorch.firestoreadvanced2compose.ui.theme.Purple80

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val uiState: HomeUiState by viewModel.uiState.collectAsState()

    Column() {
        Text(
            text = "Hi, Yorch",
            fontSize = 24.sp,
            color = Gray,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp)
        )
        Balance(uiState.isLoading, uiState.totalAmount)
        Text(
            text = "Recent Transactions",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Transactions(uiState.transactions)
    }
}

@Composable
fun Transactions(transactions: List<TransactionModel>) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionModel) {
    Row(
        modifier = Modifier.padding(24.dp).fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_money),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column(

        ) {
            Text(text = transaction.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Lunes 23 Marzo", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "${transaction.amountFormatted()}€", color = Color.Red)
    }
}

@Composable
fun Balance(isLoading: Boolean, totalAmount: String?) {
    Card(
        modifier = Modifier.padding(24.dp).wrapContentHeight()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(brush = Brush.verticalGradient(listOf(Purple80, Purple40)))
                .padding(24.dp)
        ) {
            Row {
                Column {
                    Text(text = "Debes", color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    if (isLoading) CircularProgressIndicator()
                    else Text(
                        "$totalAmount€",
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {

                }) {
                    Image(
                        painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

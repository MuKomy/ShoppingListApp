package com.example.shoppinglist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                ShoppingList()
                }
            }
        }
    }
}


@Composable
fun ShoppingList() {

    val context = LocalContext.current
    var sItems by remember { mutableStateOf(listOf<ShoppingItems>()) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    var quantityValue by remember { mutableStateOf("") }
    //var quantityValueToInt by remember { mutableIntStateOf( quantityValue.toInt()) }
    val quantityValueToInt = quantityValue.toIntOrNull() ?:0
    if(showAlertDialog){
        AlertDialog(

            onDismissRequest = {showAlertDialog = false},
            confirmButton = { Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement =Arrangement.SpaceBetween) {

                Button(
                    onClick = { showAlertDialog = false },
                    //modifier = Modifier.align(Alignment.CenterStart)
                )
                {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        if(textValue.isNotBlank() && quantityValueToInt > 0){
                            val newItem = ShoppingItems(
                                id = sItems.size +1,
                                name = textValue,
                                quantity =quantityValueToInt
                            )
                            sItems += newItem
                            textValue = ""
                            quantityValue = ""
                            showAlertDialog = false
                            Toast.makeText( context,"Added ${newItem.quantity} of ${newItem.name} to ID ${newItem.id}" ,Toast.LENGTH_SHORT ).show()
                            //Toast.makeText( context,"Worked ${quantityValue.toIntOrNull()}" ,Toast.LENGTH_SHORT ).show()
                            //Toast.makeText( context,"Worked to int value $quantityValueToInt" ,Toast.LENGTH_SHORT ).show()
                        }else{

                            Toast.makeText( context,"Please Enter Valid Data" ,Toast.LENGTH_SHORT ).show()
                            //Toast.makeText( context,"Worked to int value $quantityValueToInt" ,Toast.LENGTH_SHORT ).show()

                        }


                              },
                    //modifier = Modifier.align(Alignment.CenterEnd)
                )
                {
                    Text(text = "Add")
                }
            }},
            title = { Text(text = "Add Item")},
            text = {
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)){
                    OutlinedTextField(
                        singleLine = true,
                        value = textValue,
                        onValueChange = {textValue = it},
                        label = {Text("Name") }
                        )
                    OutlinedTextField(
                        singleLine = true,
                        value = quantityValue,
                        onValueChange = {quantityValue = it},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {Text("Quantity") }
                        )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        )
    }
    LazyColumn(modifier = Modifier.padding(8.dp)){
        items(sItems){

        }
    }
    Row (horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom){
        ExtendedFloatingActionButton(
            onClick = {showAlertDialog = true },
            icon = { Icon(Icons.Filled.Add, "Add items button.") },
            text = { Text(text = "Add Items") },
            modifier = Modifier
                //.align(Alignment.BottomEnd)
                .padding(16.dp)

        )

    }



        }

data class ShoppingItems(val id : Int, val name :String, var quantity :Int, val isEditing :Boolean = false)

@Preview(showBackground = true)

@Composable
fun ShoppingListPreview(){
    ShoppingList()

}


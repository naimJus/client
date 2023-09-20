package com.example.bankclients.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.bankclients.ui.theme.BankClientsTheme
import com.example.domain.model.Result
import com.example.domain.usecase.GetUsersUseCase
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any?> {
        return androidInjector
    }

    @Inject
    lateinit var useCase: GetUsersUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        lifecycleScope.launch{
            val result = useCase.invoke(true)
            when(result){
                is Result.Error -> Log.d("TAG", "onCreate: ${result.error}")
                is Result.Success -> Log.d("TAG", "onCreate: ${result.data.joinToString(",")}")
            }
        }
        setContent {
            BankClientsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BankClientsTheme {
        Greeting("Android")
    }
}
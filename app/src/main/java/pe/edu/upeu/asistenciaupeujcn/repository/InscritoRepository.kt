package pe.edu.upeu.asistenciaupeujcn.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.asistenciaupeujcn.data.local.dao.InscritoDao
import pe.edu.upeu.asistenciaupeujcn.data.remote.RestInscrito
import pe.edu.upeu.asistenciaupeujcn.modelo.Inscrito
import pe.edu.upeu.asistenciaupeujcn.utils.TokenUtils
import pe.edu.upeu.asistenciaupeujcn.utils.isNetworkAvailable
import javax.inject.Inject

interface InscritoRepository {
    fun reportarInscrito(): LiveData<List<Inscrito>>

}

    class InscritoRepositoryImpl @Inject constructor(
    private val restInscrito: RestInscrito,
    private val inscritoDao: InscritoDao
    ): InscritoRepository{
        override fun reportarInscrito(): LiveData<List<Inscrito>> {
            val inscritosLiveData = MutableLiveData<List<Inscrito>>()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    delay(3000)  // Simulamos un retraso
                    if (isNetworkAvailable(TokenUtils.CONTEXTO_APPX)) {
                        val response = restInscrito.reportarInscrito(TokenUtils.TOKEN_CONTENT)
                        if (response.isSuccessful && response.body() != null) {
                            val data = response.body()!!
                            inscritosLiveData.postValue(data)  // Actualizar el LiveData con los datos de la API
                        } else {
                            Log.e("ERROR", "Error en la respuesta de la API: ${response.errorBody()?.string()}")
                        }
                    } else {
                        Log.e("ERROR", "No hay conexión de red")
                    }
                } catch (e: Exception) {
                    Log.e("ERROR", "Excepción: ${e.message}")
                }
            }

            return inscritosLiveData
        }

}
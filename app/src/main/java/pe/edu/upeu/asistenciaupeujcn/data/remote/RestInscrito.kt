package pe.edu.upeu.asistenciaupeujcn.data.remote

import pe.edu.upeu.asistenciaupeujcn.modelo.Actividad
import pe.edu.upeu.asistenciaupeujcn.modelo.Inscrito
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface RestInscrito {
    @GET("/asis/inscrito/list")
    suspend fun reportarInscrito(@Header("Authorization") token:String): Response<List<Inscrito>>
}
package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaConAnticipacion  implements ValidacionDeConsultas {

    public void validar(DatosReservaConsulta datos){

        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora,fechaConsulta).toMinutes(); // ver duración entre dos localDateTime

        if(diferenciaEnMinutos < 30){
            throw  new ValidacionException("Horario seleccionado con menos de 30 minutos de anticipación");
        }
    }
}

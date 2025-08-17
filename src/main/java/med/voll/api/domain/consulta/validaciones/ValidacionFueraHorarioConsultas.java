package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraHorarioConsultas implements ValidacionDeConsultas {

    public void validar(DatosReservaConsulta datos){

        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierreClinica = fechaConsulta.getHour() > 18;

        if(domingo || horarioAntesAperturaClinica || horarioDespuesDeCierreClinica){
            throw  new ValidacionException("Horario seleccionado fuera del horario de servicio");
        }
    }

}

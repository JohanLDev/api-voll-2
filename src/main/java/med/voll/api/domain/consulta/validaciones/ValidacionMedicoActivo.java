package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoActivo implements ValidacionDeConsultas {

    // Usamos autowired si tenemos una  anotación como component o service, por que spring debe reconocerlo
    @Autowired
    MedicoRepository repository;

    public void validar(DatosReservaConsulta datos){

        if(datos.idMedico() == null){
            return;
        }

        var medicoActivo = repository.findActivoById(datos.idMedico());

        if(!medicoActivo){
            throw new ValidacionException("Medico inhabilitado para consultas");
        }

    }
}

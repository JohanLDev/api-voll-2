package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.MotivoCancelamiento;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Para indicarle a spring que esta es una clase de test
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE) // Para que use nuestra base de datos propia para los test
@ActiveProfiles("test") // Se la da a conocer el nuevo archivo de configuración que deberá usar (ahí tenemos la configuración a la base de datos)
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Debera devolver null cuando el médico buscado existe pero no está disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFecha() {
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("medico 1", "medico@gmail.com" , "12345678", Especialidad.ORTOPEDIA);
        var paciente = registrarPaciente("Paciente 1" , "paciente@gmail.com" , "123456");
        registrarConsulta(medico,paciente,lunesSiguienteALas10);

        var medicoLibre = repository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.ORTOPEDIA,lunesSiguienteALas10);

        assertThat(medicoLibre).isNull();
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, MotivoCancelamiento.OTROS));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "6145489789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234564",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle x",
                "distrito y",
                "ciudad z",
                "123",
                "1",
                "12312",
                ""
        );
    }






}
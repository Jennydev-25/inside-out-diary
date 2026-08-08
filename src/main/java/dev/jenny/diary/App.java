package dev.jenny.diary;

import java.time.LocalDate;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;
import dev.jenny.diary.views.AccessView;

/**
 * Entry point of the console app.
 */
public final class App {

    private App() {
    }

    /**
     * Launches the diary, starting with the password gate. Adds a
     * handful of example moments beforehand so the diary isn't empty
     * on first run.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        addExampleMoments(DiaryControllerSingleton.getInstance());
        AccessView.printAccessMenu();
    }

    /**
     * Adds one example moment per emotion through the given controller.
     *
     * @param diaryController the controller to add moments through
     */
    static void addExampleMoments(DiaryController diaryController) {
        diaryController.addMoment(new MomentDto(null, "Aprobé el examen que más miedo me daba",
                "Salí de clase flotando, no me lo esperaba en absoluto. Llamé a mis padres nada más salir "
                        + "por la puerta. (Esto es un momento de ejemplo, bórralo cuando quieras.)",
                Emotion.ALEGRIA, LocalDate.of(2026, 1, 15)));
        diaryController.addMoment(new MomentDto(null, "Me despedí de mi mejor amiga en el aeropuerto",
                "Se muda a otro país por trabajo y no sé cuándo volveré a verla. El vuelo se retrasó una "
                        + "hora y tuvimos que alargar la despedida sin saber qué más decirnos. "
                        + "(Momento de ejemplo, puedes eliminarlo desde el menú.)",
                Emotion.TRISTEZA, LocalDate.of(2026, 2, 10)));
        diaryController.addMoment(new MomentDto(null, "Me cancelaron el tren sin avisar",
                "Perdí la conexión y tuve que esperar tres horas en el andén sin cobertura para avisar a "
                        + "nadie. Cuando por fin llegué, ya se habían ido todos. "
                        + "(Ejemplo incluido con la app, no hace falta conservarlo.)",
                Emotion.IRA, LocalDate.of(2026, 2, 28)));
        diaryController.addMoment(new MomentDto(null, "Encontré comida caducada en la nevera",
                "Llevaba ahí semanas y no me había dado cuenta hasta que abrí el táper. Tuve que tirar "
                        + "medio estante entero. (Es solo un ejemplo, siéntete libre de borrarlo.)",
                Emotion.ASCO, LocalDate.of(2026, 3, 12)));
        diaryController.addMoment(new MomentDto(null, "Se fue la luz mientras estaba sola en casa",
                "Tardé un buen rato en encontrar las velas a tientas por el pasillo. Cada ruido de fuera "
                        + "sonaba el doble de fuerte. (Momento de muestra, se puede borrar sin problema.)",
                Emotion.MIEDO, LocalDate.of(2026, 4, 5)));
        diaryController.addMoment(new MomentDto(null, "Entrevista de trabajo importante",
                "No pegué ojo la noche antes pensando en todo lo que podía salir mal. Llegué media hora "
                        + "antes solo para no arriesgarme a llegar tarde. "
                        + "(Ejemplo de partida, bórralo si no lo necesitas.)",
                Emotion.ANSIEDAD, LocalDate.of(2026, 4, 20)));
        diaryController.addMoment(new MomentDto(null, "Mi compañero consiguió el ascenso que yo quería",
                "Me alegré por él delante de todos, aunque por dentro me costó bastante. Esa noche no dejé "
                        + "de darle vueltas. (Esto viene de ejemplo, puedes quitarlo cuando quieras.)",
                Emotion.ENVIDIA, LocalDate.of(2026, 5, 18)));
        diaryController.addMoment(new MomentDto(null, "Se me rompió el pantalón en medio de la calle",
                "Tuve que volver a casa tapándome como pude, notando cómo me miraba la gente. Tardé días "
                        + "en poder contarlo sin ponerme colorada. "
                        + "(Momento de ejemplo, no pasa nada si lo borras.)",
                Emotion.VERGUENZA, LocalDate.of(2026, 6, 2)));
        diaryController.addMoment(new MomentDto(null, "Tarde de domingo sin planes",
                "No sabía qué hacer y acabé viendo el techo un buen rato. Al final ni siquiera encendí la "
                        + "tele. (Incluido como ejemplo, bórralo cuando te apetezca.)",
                Emotion.ABURRIMIENTO, LocalDate.of(2026, 6, 25)));
        diaryController.addMoment(new MomentDto(null, "Encontré fotos del colegio en una caja",
                "Me pasé una hora recordando a gente que no veo desde hace años. Alguna ni siquiera "
                        + "recordaba cómo se llamaba. "
                        + "(Último ejemplo — el resto del diario ya es cosa tuya.)",
                Emotion.NOSTALGIA, LocalDate.of(2026, 7, 30)));
    }
}

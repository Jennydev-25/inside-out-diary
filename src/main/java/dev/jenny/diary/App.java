package dev.jenny.diary;

import java.time.LocalDate;
import java.util.List;

import dev.jenny.diary.controllers.DiaryController;
import dev.jenny.diary.dtos.MomentDto;
import dev.jenny.diary.models.Emotion;
import dev.jenny.diary.singletons.DiaryControllerSingleton;
import dev.jenny.diary.views.AccessView;

/**
 * Entry point of the console app.
 */
public final class App {

    private static final List<ExampleMoment> EXAMPLE_MOMENTS = List.of(
            new ExampleMoment("Aprobé el examen que más miedo me daba",
                    "Salí de clase flotando, no me lo esperaba en absoluto. Llamé a mis padres nada más "
                            + "salir por la puerta. (Esto es un momento de ejemplo, bórralo cuando quieras.)",
                    Emotion.ALEGRIA, LocalDate.of(2026, 1, 15)),
            new ExampleMoment("Me despedí de mi mejor amiga en el aeropuerto",
                    "Se muda a otro país por trabajo y no sé cuándo volveré a verla. El vuelo se retrasó "
                            + "una hora y tuvimos que alargar la despedida sin saber qué más decirnos. "
                            + "(Momento de ejemplo, puedes eliminarlo desde el menú.)",
                    Emotion.TRISTEZA, LocalDate.of(2026, 2, 10)),
            new ExampleMoment("Me cancelaron el tren sin avisar",
                    "Perdí la conexión y tuve que esperar tres horas en el andén sin cobertura para avisar "
                            + "a nadie. Cuando por fin llegué, ya se habían ido todos. "
                            + "(Ejemplo incluido con la app, no hace falta conservarlo.)",
                    Emotion.IRA, LocalDate.of(2026, 2, 28)),
            new ExampleMoment("Encontré comida caducada en la nevera",
                    "Llevaba ahí semanas y no me había dado cuenta hasta que abrí el táper. Tuve que tirar "
                            + "medio estante entero. (Es solo un ejemplo, siéntete libre de borrarlo.)",
                    Emotion.ASCO, LocalDate.of(2026, 3, 12)),
            new ExampleMoment("Se fue la luz mientras estaba sola en casa",
                    "Tardé un buen rato en encontrar las velas a tientas por el pasillo. Cada ruido de "
                            + "fuera sonaba el doble de fuerte. "
                            + "(Momento de muestra, se puede borrar sin problema.)",
                    Emotion.MIEDO, LocalDate.of(2026, 4, 5)),
            new ExampleMoment("Entrevista de trabajo importante",
                    "No pegué ojo la noche antes pensando en todo lo que podía salir mal. Llegué media "
                            + "hora antes solo para no arriesgarme a llegar tarde. "
                            + "(Ejemplo de partida, bórralo si no lo necesitas.)",
                    Emotion.ANSIEDAD, LocalDate.of(2026, 4, 20)),
            new ExampleMoment("Mi compañero consiguió el ascenso que yo quería",
                    "Me alegré por él delante de todos, aunque por dentro me costó bastante. Esa noche no "
                            + "dejé de darle vueltas. (Esto viene de ejemplo, puedes quitarlo cuando quieras.)",
                    Emotion.ENVIDIA, LocalDate.of(2026, 5, 18)),
            new ExampleMoment("Se me rompió el pantalón en medio de la calle",
                    "Tuve que volver a casa tapándome como pude, notando cómo me miraba la gente. Tardé "
                            + "días en poder contarlo sin ponerme colorada. "
                            + "(Momento de ejemplo, no pasa nada si lo borras.)",
                    Emotion.VERGUENZA, LocalDate.of(2026, 6, 2)),
            new ExampleMoment("Tarde de domingo sin planes",
                    "No sabía qué hacer y acabé viendo el techo un buen rato. Al final ni siquiera encendí "
                            + "la tele. (Incluido como ejemplo, bórralo cuando te apetezca.)",
                    Emotion.ABURRIMIENTO, LocalDate.of(2026, 6, 25)),
            new ExampleMoment("Encontré fotos del colegio en una caja",
                    "Me pasé una hora recordando a gente que no veo desde hace años. Alguna ni siquiera "
                            + "recordaba cómo se llamaba. (Último ejemplo — el resto del diario ya es cosa "
                            + "tuya.)",
                    Emotion.NOSTALGIA, LocalDate.of(2026, 7, 30)));

    private App() {
    }

    /**
     * Launches the diary, adding the example moments first.
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
        for (ExampleMoment example : EXAMPLE_MOMENTS) {
            diaryController.addMoment(new MomentDto(null, example.title(), example.description(),
                    example.emotion(), example.date()));
        }
    }

    /** Holds one example moment's data before it becomes a MomentDto. */
    private record ExampleMoment(String title, String description, Emotion emotion, LocalDate date) {
    }
}

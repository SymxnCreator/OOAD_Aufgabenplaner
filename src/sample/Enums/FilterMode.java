package sample.Enums;

/**
 * Definiert einen Modus, nach welchen Kriterien Aufgaben gefiltert werden können.
 */
public enum FilterMode
{
    Date("Nach Datum"),
    Priority("Nach Priorität");

    private String mode;

    private FilterMode(String mode)
    {
        this.mode = mode;
    }
}
package sample.Enums;

/**
 * Definiert, wann der Benutzer an eine Aufgabe erinnert werden soll.
 * @author Simon Schnitker
 */
public enum NotificationTime
{
    /**
     * Keine Errinnerung.
     */
    Never,
    /**
     * Eine Stunde vorher.
     */
    Hour,
    /**
     * Einen Tag vorher.
     */
    Day,
    /**
     * Eine Woche vorher.
     */
    Week
}
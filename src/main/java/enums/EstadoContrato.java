package enums;

/**
 * Estados posibles de un contrato.
 */
public enum EstadoContrato {
    VIGENTE,     // el contrato está en curso
    VENCIDO,     // ya pasó la fecha de fin
    RESCINDIDO   // se cortó antes de tiempo; es definitivo
}

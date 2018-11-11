package ba.klika.androidtemplate.scheduling

import io.reactivex.Scheduler

/**
 * Provider for [Scheduler]s used in application
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SchedulingProvider {
    /**
     * I/O scheduler - this should be a background thread
     */
    fun io(): Scheduler

    /**
     * Main scheduler - for UI - connected to main thread
     */
    fun main(): Scheduler

    /**
     * Computation scheduler - background thread but should not be used for I/O
     * It should rather be used for CPU heavy computation work
     */
    fun computation(): Scheduler

    /**
     * Single threaded background scheduler
     */
    fun single(): Scheduler
}
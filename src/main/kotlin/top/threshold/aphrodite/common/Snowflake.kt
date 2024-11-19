package top.threshold.aphrodite.common

class Snowflake(private val dataCenterId: Int, private val workerId: Int) {
    companion object {
        const val EPOCH = 1_577_836_800_000L
        const val SEQUENCE_BITS = 12L
        const val WORKER_ID_BITS = 5L
        const val DATA_CENTER_ID_BITS = 5L

        const val MAX_WORKER_ID = (1L shl WORKER_ID_BITS.toInt()) - 1
        const val MAX_DATA_CENTER_ID = (1L shl DATA_CENTER_ID_BITS.toInt()) - 1
        const val SEQUENCE_MASK = (1L shl SEQUENCE_BITS.toInt()) - 1

        const val WORKER_ID_SHIFT = SEQUENCE_BITS
        const val DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS
        const val TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS
    }

    private var sequence: Long = 0
    private var lastTimestamp: Long = -1

    init {
        require(dataCenterId in 0..MAX_DATA_CENTER_ID) { "dataCenterId must be between 0 and $MAX_DATA_CENTER_ID" }
        require(workerId in 0..MAX_WORKER_ID) { "workerId must be between 0 and $MAX_WORKER_ID" }
    }

    fun generateId(): Long {
        var timestamp = currentTimeMillis()

        if (timestamp < lastTimestamp) {
            throw IllegalStateException("The system clock is set back and ID generation fails.")
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) and SEQUENCE_MASK
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0
        }

        lastTimestamp = timestamp

        return (timestamp - EPOCH shl TIMESTAMP_SHIFT.toInt()) or
            (dataCenterId.toLong() shl DATA_CENTER_ID_SHIFT.toInt()) or
            (workerId.toLong() shl WORKER_ID_SHIFT.toInt()) or
            sequence
    }

    private fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis()
        }
        return timestamp
    }
}

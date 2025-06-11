package top.threshold.aphrodite.pkg.utils

object SnowflakeUtil {
    // 起始时间戳：2020-01-01 00:00:00
    private const val START_TIMESTAMP = 1577836800000L

    private const val SEQUENCE_BITS = 12L
    private const val MACHINE_BITS = 5L
    private const val DATACENTER_BITS = 5L

    private val MAX_SEQUENCE = (-1L shl SEQUENCE_BITS.toInt()).inv()

    private val MACHINE_SHIFT = SEQUENCE_BITS
    private val DATACENTER_SHIFT = SEQUENCE_BITS + MACHINE_BITS
    private val TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_BITS + DATACENTER_BITS

    private const val MACHINE_ID = 1L // 你可以改成从配置读取
    private const val DATACENTER_ID = 1L

    private var sequence = 0L
    private var lastTimestamp = -1L

    @Synchronized
    fun nextId(): Long {
        var timestamp = System.currentTimeMillis()

        if (timestamp < lastTimestamp) {
            throw RuntimeException("系统时钟回退，拒绝生成ID")
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                timestamp = waitNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0L
        }

        lastTimestamp = timestamp

        return (((timestamp - START_TIMESTAMP) shl TIMESTAMP_SHIFT.toInt())
                or (DATACENTER_ID shl DATACENTER_SHIFT.toInt())
                or (MACHINE_ID shl MACHINE_SHIFT.toInt())
                or sequence)
    }

    private fun waitNextMillis(lastTimestamp: Long): Long {
        var timestamp = System.currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}

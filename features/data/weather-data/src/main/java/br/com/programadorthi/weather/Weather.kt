package br.com.programadorthi.weather

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.StringDescriptor
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

@Serializable
data class WeatherRaw(
    @SerialName("woeid") val locationId: Int,
    @SerialName("title") val location: String,
    @SerialName("consolidated_weather") val weathers: List<Weather>
)

@Serializable
data class WeatherCity(
    @SerialName("title") val title: String,
    @SerialName("location_type") val locationType: String,
    @SerialName("woeid") val locationId: Int,
    @SerialName("latt_long") val latLong: String
)

@Serializable
data class Weather(
    @Serializable(with = WeatherConditionSerializer::class)
    @SerialName("weather_state_abbr") val condition: WeatherCondition,
    @SerialName("weather_state_name") val formmattedCondition: String,
    @SerialName("min_temp") val minTemp: Double,
    @SerialName("the_temp") val temp: Double,
    @SerialName("max_temp") val maxTemp: Double,
    @SerialName("created") val created: String,
    @Serializable(with = DateSerializer::class) val lastUpdated: Date = Calendar.getInstance().time
)

@Serializer(forClass = WeatherCondition::class)
object WeatherConditionSerializer : KSerializer<WeatherCondition> {

    override val descriptor: SerialDescriptor
        get() = StringDescriptor

    override fun deserialize(decoder: Decoder): WeatherCondition {
        return when (decoder.decodeString()) {
            "sn" -> WeatherCondition.Snow
            "sl" -> WeatherCondition.Sleet
            "h" -> WeatherCondition.Hail
            "t" -> WeatherCondition.Thunderstorm
            "hr" -> WeatherCondition.HeavyRain
            "lr" -> WeatherCondition.LightRain
            "s" -> WeatherCondition.Showers
            "hc" -> WeatherCondition.HeavyCloud
            "lc" -> WeatherCondition.LightCloud
            "c" -> WeatherCondition.Clear
            else -> WeatherCondition.Unknown
        }
    }

    override fun serialize(encoder: Encoder, obj: WeatherCondition) {
        val value = when (obj) {
            WeatherCondition.Snow -> "sn"
            WeatherCondition.Sleet -> "sl"
            WeatherCondition.Hail -> "h"
            WeatherCondition.Thunderstorm -> "t"
            WeatherCondition.HeavyRain -> "hr"
            WeatherCondition.LightRain -> "lr"
            WeatherCondition.Showers -> "s"
            WeatherCondition.HeavyCloud -> "hc"
            WeatherCondition.LightCloud -> "lc"
            WeatherCondition.Clear -> "c"
            else -> ""
        }
        encoder.encodeString(value)
    }
}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {

    private val dateFormat = DateFormat.getDateTimeInstance()

    override val descriptor: SerialDescriptor
        get() = StringDescriptor

    override fun deserialize(decoder: Decoder): Date {
        val value = decoder.decodeString()
        return dateFormat.parse(value)
            ?: throw IllegalStateException("The [$value] date value is an invalid format.")
    }

    override fun serialize(encoder: Encoder, obj: Date) {
        val value = dateFormat.format(obj)
        encoder.encodeString(value)
    }
}

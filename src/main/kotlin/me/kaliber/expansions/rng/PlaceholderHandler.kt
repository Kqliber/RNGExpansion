package me.kaliber.expansions.rng

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.OfflinePlayer
import kotlin.math.max
import kotlin.math.min

internal class RNGPlaceholderHandler
{

    // will keep track of the last random number generated for the %rng_last_generated% placeholder
    private var lastNumber = 0

    internal fun handle(player: OfflinePlayer, identifier: String): String?
    {

        // sets input to allow for bracket placeholders to be randomized
        val input = PlaceholderAPI.setBracketPlaceholders(player, identifier)

        return when
        {
            // returns a random number from 1-2147483647
            input == "random" ->
            {
                val number = (1..Int.MAX_VALUE).random()
                lastNumber = number
                number.toString()
            }

            // returns the last generated number from this expansion
            input == "last_generated" -> lastNumber.toString()

            // returns a random number that are being inputted between ','
            input.contains(',') ->
            {
                val args = input.split(',')
                val first = args[0].convertToInt()
                val second = args[1].convertToInt()

                if (first == null || second == null)
                {
                    return null
                }

                val min = min(first, second)
                val max = max(first, second)

                val number = (min..max).random()
                lastNumber = number
                number.toString()
            }
            else -> null
        }
    }

    // allows for decimals to be inputted
    private fun String.convertToInt(): Int?
    {
        return this.toDoubleOrNull()?.toInt()
    }
}

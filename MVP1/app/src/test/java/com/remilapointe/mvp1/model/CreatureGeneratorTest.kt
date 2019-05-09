package com.remilapointe.mvp1.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreatureGeneratorTest {
    private lateinit var creatureGenerator: CreatureGenerator

    @Before
    fun setup() {
        creatureGenerator = CreatureGenerator()
    }

    @Test
    fun testGeneratorHitPoints() {
        val attributes = CreatureAttributes(
            intelligence = 7,
            strength = 3,
            endurance = 10
        )
        val name = "Rikachu"
        val expectedCredture = Creature(attributes, 84, name)

        assertEquals(expectedCredture, creatureGenerator.generateCreature(attributes, name))
    }
}
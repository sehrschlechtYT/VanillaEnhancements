package yt.sehrschlecht.vanillaenhancements.modules.inbuilt

import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Ageable
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.block.EntityBlockFormEvent
import org.bukkit.event.entity.*
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption
import yt.sehrschlecht.vanillaenhancements.modules.ModuleCategory
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.VEModule

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class MobGriefingControl : VEModule(
    "Allows to control the mob griefing game rule per mob.",
    ModuleCategory.INBUILT,
    ModuleTag.WORLD,
    ModuleTag.ENTITIES
) {
    val allayItemPickup = BooleanOption(true, "Controls if allays can pick up dropped items.")
    val blazeFireCreation = BooleanOption(true, "Controls if fireballs shot by blazes can create fire and light campfires.")
    val creeperExplosion = BooleanOption(true, "Controls if creeper explosions destroy blocks.")
    val endCrystalExplosion = BooleanOption(true, "Controls if end crystal explosions destroy blocks.")
    val enderDragonBlockDestruction = BooleanOption(true, "Controls if ender dragons can destroy blocks.")
    val endermanBlockModification = BooleanOption(true, "Controls if endermen can pick up and place blocks.")
    val evokerSheepColorConversion = BooleanOption(true, "Controls if evokers can turn blue sheep red.")
    val foxSweetBerryPick = BooleanOption(true, "Controls if foxes can pick sweet berries from sweet berry bushes.")
    val foxItemPickup = BooleanOption(true, "Controls if foxes can pick up dropped items.")
    val ghastFireballExplosion = BooleanOption(true, "Controls if fireballs shot by ghasts can damage blocks and create fire.")
    val piglinItemPickup = BooleanOption(true, "Controls if piglins can pick up dropped items.")
    val rabbitEatCarrotCrop = BooleanOption(true, "Controls if rabbits can eat carrot crops.")
    val ravagerDestroyCrops = BooleanOption(true, "Controls is ravagers can destroy crops.")
    val ravagerDestroyLeaves = BooleanOption(true, "Controls if ravagers can destroy leaves.")
    val sheepEatGrass = BooleanOption(true, "Controls if sheep can turn grass into dirt.")
    val silverfishModifyInfestedBlock = BooleanOption(true, "Controls if silverfish can hide in infested blocks or hatch from them.")
    val snowGolemTrail = BooleanOption(true, "Controls if snow golems can create snow trails.")
    val villagerFarm = BooleanOption(true, "Controls if villagers can farm.")
    val villagerItemPickup = BooleanOption(true, "Controls if villagers can pick up items.")
    val witherExplosion = BooleanOption(true, "Controls if wither explosions destroy blocks.")
    val zombieBreakDoor = BooleanOption(true, "Controls if zombies can break doors.")
    val zombieItemPickup = BooleanOption(true, "Controls if zombies can pick up items.")
    val zombieTurtleEggAttack = BooleanOption(true, "Controls if zombies can attack turtle eggs.") // todo overwrite pathfinding goal if possible

    override fun getKey(): String {
        return "mob_griefing_control"
    }

    /**
     * @return An instance of the plugin this module belongs to.
     */
    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

    override fun getDisplayItem(): Material {
        return Material.CREEPER_HEAD
    }

    @EventHandler(ignoreCancelled = true)
    fun onItemPickup(event: EntityPickupItemEvent) {
        when (event.entity.type) {
            EntityType.ALLAY -> { // allay item pickup
                if (allayItemPickup.get()) return
                event.isCancelled = true
            }
            EntityType.FOX -> { // fox item pickup
                if (foxItemPickup.get()) return
                event.isCancelled = true
            }
            EntityType.PIGLIN -> { // piglin item pickup
                if (piglinItemPickup.get()) return
                event.isCancelled = true
            }
            EntityType.VILLAGER -> { // villager item pickup
                if (villagerItemPickup.get()) return
                event.isCancelled = true
            }
            EntityType.ZOMBIE -> { // zombie item pickup
                if (zombieItemPickup.get()) return
                event.isCancelled = true
            }

            else -> {}
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        if (event.entityType == EntityType.FIREBALL) {
            val fireball = event.entity as Fireball
            fireball.shooter ?: return
            if (fireball.shooter is Blaze) { // blaze fireball explosion
                if (blazeFireCreation.get()) return
                fireball.setIsIncendiary(false)
                fireball.yield = 0F
            } else if (fireball.shooter is Ghast) { // ghast fireball explosion
                if (ghastFireballExplosion.get()) return
                fireball.setIsIncendiary(false)
                fireball.yield = 0F
            } else if (fireball is WitherSkull && fireball.shooter is Wither) { // wither skull explosion
                if (witherExplosion.get()) return
                fireball.setIsIncendiary(false)
                fireball.yield = 0F
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        when (event.entityType) {
            EntityType.CREEPER -> { // creeper explosion
                if (creeperExplosion.get()) return
                event.blockList().clear()
            }
            EntityType.ENDER_CRYSTAL -> { // end crystal explosion
                if (endCrystalExplosion.get()) return
                event.blockList().clear()
            }
            EntityType.ENDER_DRAGON -> { // ender dragon block destruction
                if (enderDragonBlockDestruction.get()) return
                event.blockList().clear()
            }
            EntityType.WITHER -> { // wither explosion
                if (witherExplosion.get()) return
                event.blockList().clear()
            }

            else -> {}
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockIgnite(event: BlockIgniteEvent) {
        if (event.cause != BlockIgniteEvent.IgniteCause.FIREBALL) return
        if (event.ignitingEntity is Blaze && !blazeFireCreation.get()) {
            event.isCancelled = true
            return
        } else if (event.ignitingEntity is Ghast && !ghastFireballExplosion.get()) {
            event.isCancelled = true
            return
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityBlockChange(event: EntityChangeBlockEvent) {
        // enderman block modification
        if (event.entityType == EntityType.ENDERMAN) {
            if (endermanBlockModification.get()) return
            event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onSpellCast(event: EntitySpellCastEvent) {
        // evoker sheep color conversion
        if (event.spell != Spellcaster.Spell.WOLOLO) return
        if (evokerSheepColorConversion.get()) return
        event.isCancelled = true
    }

    @EventHandler(ignoreCancelled = true)
    fun onEntityChangeBlock(event: EntityChangeBlockEvent) {
        when (event.entityType) {
            EntityType.FOX -> { // foxes picking sweet berries
                if (event.block.type != Material.SWEET_BERRY_BUSH) return
                if (foxSweetBerryPick.get()) return
                event.isCancelled = true
            }
            EntityType.RABBIT -> { // rabbit eating carrots
                if (event.block.type != Material.CARROTS) return
                if (rabbitEatCarrotCrop.get()) return
                event.isCancelled = true
            }
            EntityType.RAVAGER -> { // ravager crop/leaves destruction
                if (event.block.type == Material.FARMLAND || event.block.getRelative(BlockFace.DOWN).type == Material.FARMLAND) {
                    if (ravagerDestroyCrops.get()) return
                    event.isCancelled = true
                } else if (event.block.type.name.endsWith("LEAVES")) {
                    if (ravagerDestroyLeaves.get()) return
                    event.isCancelled = true
                }
            }
            EntityType.SHEEP -> { // sheep eating grass
                if (event.block.type != Material.GRASS_BLOCK && event.block.type != Material.GRASS) return
                if (sheepEatGrass.get()) return
                event.isCancelled = true
            }
            EntityType.SILVERFISH -> { // silverfish modify infested block
                if (silverfishModifyInfestedBlock.get()) return
                event.isCancelled = true
            }
            EntityType.VILLAGER -> { // villager farm
                if (event.block.state.data !is Ageable) return
                if (villagerFarm.get()) return
                event.isCancelled = true
            }

            else -> {}
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInteract(event: EntityInteractEvent) {
        if (event.block.type == Material.FARMLAND || event.block.getRelative(BlockFace.DOWN).type == Material.FARMLAND) { // ravager crop destruction
            if (event.entityType != EntityType.RAVAGER) return
            if (ravagerDestroyCrops.get()) return
            event.isCancelled = true
        } else if (event.block.type == Material.TURTLE_EGG) { // zombie turtle egg attack
            if (event.entityType != EntityType.ZOMBIE) return
            if (zombieTurtleEggAttack.get()) return
            event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockForm(event: EntityBlockFormEvent) {
        // snow golems leaving a snow trail
        if (event.entity.type != EntityType.SNOWMAN) return
        if (snowGolemTrail.get()) return
        event.isCancelled = true
    }

    @EventHandler(ignoreCancelled = true)
    fun onDoorBreak(event: EntityBreakDoorEvent) {
        // zombies breaking doors
        if (event.entityType != EntityType.ZOMBIE) return
        if (zombieBreakDoor.get()) return
        event.isCancelled = true
    }

}
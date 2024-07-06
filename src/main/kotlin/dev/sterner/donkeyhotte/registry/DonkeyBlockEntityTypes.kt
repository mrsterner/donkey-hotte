package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.ChopperBlockEntity
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import dev.sterner.donkeyhotte.registry.DonkeyBlocks.BLOCKS
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import java.util.function.Consumer
import java.util.function.Supplier


object DonkeyBlockEntityTypes {

    val BLOCK_ENTITY_TYPES = LazyRegistrar.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Donkeyhotte.MOD_ID)

    val GRINDSTONE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("grindstone_block") {
        BlockEntityType.Builder.of({ pos, state -> GrindstoneBlockEntity(pos, state) }, DonkeyBlocks.GRINDSTONE_BLOCK.get())
            .build()
    }

    val CHOPPER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("choppere_block") {
        BlockEntityType.Builder.of({ pos, state -> ChopperBlockEntity(pos, state) }, DonkeyBlocks.CHOPPER_BLOCK.get())
            .build()
    }

    fun init(){

    }
}

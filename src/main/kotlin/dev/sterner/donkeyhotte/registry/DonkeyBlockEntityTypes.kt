package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityType
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import java.util.function.Supplier


object DonkeyBlockEntityTypes {

    val GRINDSTONE_BLOCK_ENTITY = register("grindstone_block") {
        BlockEntityType.Builder.of({ pos, state -> GrindstoneBlockEntity(pos, state) }, DonkeyBlocks.GRINDSTONE_BLOCK)
            .build()
    }

    private fun <T : BlockEntity> register(name: String, blockEntityType: Supplier<BlockEntityType<T>>): BlockEntityType<T> {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Donkeyhotte.id(name), blockEntityType.get())
        return blockEntityType.get()
    }

    fun init(){

    }
}
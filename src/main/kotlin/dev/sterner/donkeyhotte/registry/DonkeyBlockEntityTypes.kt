package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
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

    val BLOCK_ENTITY_TYPES: MutableMap<Supplier<BlockEntityType<*>>, ResourceLocation> = mutableMapOf<Supplier<BlockEntityType<*>>, ResourceLocation>()

    val GRINDSTONE_BLOCK_ENTITY = register("grindstone_block") {
        BlockEntityType.Builder.of({ pos, state -> GrindstoneBlockEntity(pos, state) }, DonkeyBlocks.GRINDSTONE_BLOCK.get())
            .build()
    }

    private fun <T : BlockEntity> register(name: String, blockEntityType: Supplier<out BlockEntityType<T>>): Supplier<out BlockEntityType<T>> {
        BLOCK_ENTITY_TYPES[blockEntityType as Supplier<BlockEntityType<*>>] = Donkeyhotte.id(name)
        return blockEntityType
    }


    fun init(){
        BLOCK_ENTITY_TYPES.keys.forEach(Consumer { blockEntityType: Supplier<BlockEntityType<*>> ->
            Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                BLOCK_ENTITY_TYPES[blockEntityType]!!,
                blockEntityType.get()
            )
        })
    }
}

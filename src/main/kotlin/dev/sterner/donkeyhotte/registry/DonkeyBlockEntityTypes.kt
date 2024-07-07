package dev.sterner.donkeyhotte.registry

import dev.sterner.donkeyhotte.Donkeyhotte
import dev.sterner.donkeyhotte.blockentity.ChopperBlockEntity
import dev.sterner.donkeyhotte.blockentity.GrindstoneBlockEntity
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType

object DonkeyBlockEntityTypes {

    val BLOCK_ENTITY_TYPES: LazyRegistrar<BlockEntityType<*>> = LazyRegistrar.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Donkeyhotte.MOD_ID)

    val GRINDSTONE_BLOCK_ENTITY: RegistryObject<BlockEntityType<GrindstoneBlockEntity>> = BLOCK_ENTITY_TYPES.register("grindstone_block") {
        BlockEntityType.Builder.of({ pos, state -> GrindstoneBlockEntity(pos, state) }, DonkeyBlocks.GRINDSTONE_BLOCK.get())
            .build()
    }

    val CHOPPER_BLOCK_ENTITY: RegistryObject<BlockEntityType<ChopperBlockEntity>> = BLOCK_ENTITY_TYPES.register("choppere_block") {
        BlockEntityType.Builder.of({ pos, state -> ChopperBlockEntity(pos, state) }, DonkeyBlocks.CHOPPER_BLOCK.get())
            .build()
    }
}

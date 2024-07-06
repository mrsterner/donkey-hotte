package dev.sterner.donkeyhotte.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BarrelBlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.stream.IntStream

abstract class DonkeyContainerBlockEntity(blockEntityType: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState
) : DonkeyBlockEntity(blockEntityType, blockPos, blockState), Container, WorldlyContainer {

    protected abstract fun getItems(): NonNullList<ItemStack>

    protected abstract fun setItems(nonNullList: NonNullList<ItemStack>?)

    fun loadFromTag(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        setItems(NonNullList.withSize(this.containerSize, ItemStack.EMPTY))
        if (compoundTag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(compoundTag, getItems(), provider)
        }
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, provider)
        this.loadFromTag(compoundTag, provider)
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, provider)
        ContainerHelper.saveAllItems(compoundTag, getItems(), false, provider)
    }

    override fun isEmpty(): Boolean {
        for (itemStack in this.getItems()) {
            if (!itemStack.isEmpty) {
                return false
            }
        }

        return true
    }

    override fun getItem(i: Int): ItemStack {
        return getItems()[i]
    }

    override fun removeItem(i: Int, j: Int): ItemStack {
        val itemStack = ContainerHelper.removeItem(this.getItems(), i, j)
        if (!itemStack.isEmpty) {
            this.setChanged()
        }

        return itemStack
    }

    override fun removeItemNoUpdate(i: Int): ItemStack {
        return ContainerHelper.takeItem(this.getItems(), i)
    }

    override fun setItem(i: Int, itemStack: ItemStack) {
        getItems()[i] = itemStack
        itemStack.limitSize(this.getMaxStackSize(itemStack))
        this.setChanged()
    }

    override fun stillValid(player: Player?): Boolean {
        return Container.stillValidBlockEntity(this, player)
    }

    override fun clearContent() {
        getItems().clear()
    }

    override fun getContainerSize(): Int {
        return getItems().size
    }

    override fun canPlaceItemThroughFace(index: Int, itemStack: ItemStack, direction: Direction?): Boolean {
        return true
    }

    override fun canTakeItemThroughFace(index: Int, stack: ItemStack, direction: Direction): Boolean {
        return true
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return IntStream.range(0, containerSize).toArray();
    }
}
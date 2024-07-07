package dev.sterner.donkeyhotte.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction.Axis
import net.minecraft.core.Vec3i
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.phys.Vec3


object VecUtils {

    val CENTER_OF_ORIGIN: Vec3 = Vec3(.5, .5, .5)

    fun rotate(vec: Vec3, rotationVec: Vec3): Vec3 {
        return rotate(vec, rotationVec.x, rotationVec.y, rotationVec.z)
    }

    fun rotate(vec: Vec3, xRot: Double, yRot: Double, zRot: Double): Vec3 {
        return rotate(rotate(rotate(vec, xRot, Axis.X), yRot, Axis.Y), zRot, Axis.Z)
    }

    fun rotateCentered(vec: Vec3, deg: Double, axis: Axis): Vec3 {
        val shift: Vec3 = getCenterOf(BlockPos.ZERO)
        return rotate(vec.subtract(shift), deg, axis)
            .add(shift)
    }

    fun rotate(vec: Vec3, deg: Double, axis: Axis): Vec3 {
        if (deg == 0.0) return vec
        if (vec === Vec3.ZERO) return vec

        val angle = (deg / 180f * Math.PI).toFloat()
        val sin = Mth.sin(angle).toDouble()
        val cos = Mth.cos(angle).toDouble()
        val x = vec.x
        val y = vec.y
        val z = vec.z

        if (axis === Axis.X) return Vec3(x, y * cos - z * sin, z * cos + y * sin)
        if (axis === Axis.Y) return Vec3(x * cos + z * sin, y, z * cos - x * sin)
        if (axis === Axis.Z) return Vec3(x * cos - y * sin, y * cos + x * sin, z)
        return vec
    }

    fun getCenterOf(pos: Vec3i): Vec3 {
        if (pos == Vec3i.ZERO) return CENTER_OF_ORIGIN
        return Vec3.atLowerCornerOf(pos)
            .add(.5, .5, .5)
    }

    fun offsetRandomly(vec: Vec3, r: RandomSource, radius: Float): Vec3 {
        return Vec3(
            vec.x + (r.nextFloat() - .5f) * 2 * radius, vec.y + (r.nextFloat() - .5f) * 2 * radius,
            vec.z + (r.nextFloat() - .5f) * 2 * radius
        )
    }
}
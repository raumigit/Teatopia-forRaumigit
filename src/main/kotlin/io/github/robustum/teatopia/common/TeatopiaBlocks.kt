package io.github.robustum.teatopia.common

import io.github.robustum.teatopia.common.block.TeaTreeBlock
import net.devtech.arrp.json.blockstate.JBlockModel
import net.devtech.arrp.json.blockstate.JState
import net.devtech.arrp.json.blockstate.JVariant
import net.devtech.arrp.json.models.JModel
import net.devtech.arrp.json.models.JTextures
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object TeatopiaBlocks {

    val teaTree by lazy {
        addBlock(TeaTreeBlock(), "tea_tree", "Tea Tree")
    }

    val teaTreeSheet by lazy {
        addBlock(TeaTreeBlock(), "tea_tree_sheet", "Tea Tree (Sheet)")
    }

    val millStone by lazy {
        addBlock(TeaTreeBlock(), "millstone", "millstone")
    }
    val blockItems: Map<Block, Item>
        get() = mutableBlockItems
    private val mutableBlockItems: MutableMap<Block, Item> = mutableMapOf()

    private fun addSimpleBlock(
        name: String,
        localizationEn: String,
        material: Material = Material.STONE,
        useDefaultState: Boolean = true,
        useDefaultModel: Boolean = true,
    ): Block = addBlock(
        Block(FabricBlockSettings.of(material)),
        name, localizationEn, useDefaultState, useDefaultModel
    )

    private fun addBlock(
        block: Block,
        name: String,
        localizationEn: String,
        useDefaultState: Boolean = true,
        useDefaultModel: Boolean = true
    ): Block {
        val id = id(name)
        val registeredBlock = Registry.register(Registry.BLOCK, id, block)
        val blockItem = TeatopiaItems.addBlockItem(block, name, localizationEn)
        mutableBlockItems[registeredBlock] = blockItem
        Teatopia.localeEn_Us.block(id, localizationEn)
        val path = id("block/$name")
        if (useDefaultState) {
            Teatopia.RESOURCE_PACK.addBlockState(
                JState.state().add(JVariant().put("", JBlockModel(path))),
                id
            )
        }
        if (useDefaultModel) {
            Teatopia.RESOURCE_PACK.addModel(
                JModel.model("block/cube_all").textures(JTextures().`var`("all", path.toString())),
                path
            )
        }
        return registeredBlock
    }
}
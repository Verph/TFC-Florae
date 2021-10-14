package tfcflorae.client.render;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockChestTFC;
import net.dries007.tfc.objects.te.TEChestTFC;
import net.dries007.tfc.util.agriculture.FruitTree;

import tfcflorae.api.registries.TFCFRegistries;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.wood.fruitwood.BlockFruitChestTFCF;
import tfcflorae.objects.te.TEFruitChest;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.SeasonalTrees;

import static tfcflorae.TFCFlorae.MODID;

@SideOnly(Side.CLIENT)
public class TESRFruitChestTFCF extends TileEntitySpecialRenderer<TEFruitChest>
{
    private static final Map<IFruitTree, ResourceLocation> SINGLE_TEXTURES = new HashMap<>();
    private static final Map<IFruitTree, ResourceLocation> DOUBLE_TEXTURES = new HashMap<>();
    private static final Map<IFruitTree, ResourceLocation> TRAP_SINGLE_TEXTURES = new HashMap<>();
    private static final Map<IFruitTree, ResourceLocation> TRAP_DOUBLE_TEXTURES = new HashMap<>();

    private static final Map<Tree, ResourceLocation> SINGLE_TEXTURES_TREE = new HashMap<>();
    private static final Map<Tree, ResourceLocation> DOUBLE_TEXTURES_TREE = new HashMap<>();
    private static final Map<Tree, ResourceLocation> TRAP_SINGLE_TEXTURES_TREE = new HashMap<>();
    private static final Map<Tree, ResourceLocation> TRAP_DOUBLE_TEXTURES_TREE = new HashMap<>();

    static
    {
        /*for (SeasonalTrees fruitTree : SeasonalTrees.values())
        {
            String name = fruitTree.getName().toLowerCase();

            //noinspection ConstantConditions
            SINGLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest/" + name + ".png"));
            DOUBLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_double/" + name + ".png"));
            TRAP_SINGLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap/" + name + ".png"));
            TRAP_DOUBLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap_double/" + name + ".png"));
        }*/

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();

            //noinspection ConstantConditions
            SINGLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest/" + name + ".png"));
            DOUBLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_double/" + name + ".png"));
            TRAP_SINGLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap/" + name + ".png"));
            TRAP_DOUBLE_TEXTURES.put(fruitTree, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap_double/" + name + ".png"));
        }

        for (int i = 0; i < BlocksTFCF.bamboo.length; i++)
        {
            SINGLE_TEXTURES_TREE.put(BlocksTFCF.bambooTrees[i], new ResourceLocation(MODID, "textures/entity/chests/chest/" + BlocksTFCF.bamboo[i] + ".png"));
            DOUBLE_TEXTURES_TREE.put(BlocksTFCF.bambooTrees[i], new ResourceLocation(MODID, "textures/entity/chests/chest_double/" + BlocksTFCF.bamboo[i] + ".png"));
            TRAP_SINGLE_TEXTURES_TREE.put(BlocksTFCF.bambooTrees[i], new ResourceLocation(MODID, "textures/entity/chests/chest_trap/" + BlocksTFCF.bamboo[i] + ".png"));
            TRAP_DOUBLE_TEXTURES_TREE.put(BlocksTFCF.bambooTrees[i], new ResourceLocation(MODID, "textures/entity/chests/chest_trap_double/" + BlocksTFCF.bamboo[i] + ".png"));
        }

        SINGLE_TEXTURES_TREE.put(TreesTFCF.CASSIA_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest/cassia_cinnamon.png"));
        DOUBLE_TEXTURES_TREE.put(TreesTFCF.CASSIA_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_double/cassia_cinnamon.png"));
        TRAP_SINGLE_TEXTURES_TREE.put(TreesTFCF.CASSIA_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap/cassia_cinnamon.png"));
        TRAP_DOUBLE_TEXTURES_TREE.put(TreesTFCF.CASSIA_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap_double/cassia_cinnamon.png"));

        SINGLE_TEXTURES_TREE.put(TreesTFCF.CEYLON_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest/ceylon_cinnamon.png"));
        DOUBLE_TEXTURES_TREE.put(TreesTFCF.CEYLON_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_double/ceylon_cinnamon.png"));
        TRAP_SINGLE_TEXTURES_TREE.put(TreesTFCF.CEYLON_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap/ceylon_cinnamon.png"));
        TRAP_DOUBLE_TEXTURES_TREE.put(TreesTFCF.CEYLON_CINNAMON_TREE, new ResourceLocation(MODID, "textures/entity/wood/fruit_tree/chests/chest_trap_double/ceylon_cinnamon.png"));
    }

    private final ModelChest simpleChest = new ModelChest();
    private final ModelChest largeChest = new ModelLargeChest();

    @Override
    public void render(TEFruitChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        int meta = 0;
        IFruitTree wood = null;
        Tree tree = null;

        if (te.hasWorld())
        {
            Block block = te.getBlockType();
            meta = te.getBlockMetadata();
            wood = te.getWood();
            tree = te.getTree();

            if (block instanceof BlockFruitChestTFCF && meta == 0)
            {
                ((BlockFruitChestTFCF) block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
                meta = te.getBlockMetadata();
            }

            te.checkForAdjacentChests();
        }

        if (te.adjacentChestZNeg != null || te.adjacentChestXNeg != null) return;

        ModelChest modelchest;
        if (te.adjacentChestXPos == null && te.adjacentChestZPos == null)
        {
            modelchest = simpleChest;

            if (destroyStage >= 0)
            {
                bindTexture(DESTROY_STAGES[destroyStage]);
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0F, 4.0F, 1.0F);
                GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            }
            else if (te.getChestType() == BlockChest.Type.TRAP && wood != null)
            {
                bindTexture(TRAP_SINGLE_TEXTURES.get(wood));
            }
            else if (wood != null)
            {
                bindTexture(SINGLE_TEXTURES.get(wood));
            }
            else if (te.getChestType() == BlockChest.Type.TRAP && tree != null)
            {
                bindTexture(TRAP_SINGLE_TEXTURES_TREE.get(tree));
            }
            else if (tree != null)
            {
                bindTexture(SINGLE_TEXTURES_TREE.get(tree));
            }
        }
        else
        {
            modelchest = largeChest;

            if (destroyStage >= 0)
            {
                bindTexture(DESTROY_STAGES[destroyStage]);
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scale(8.0F, 4.0F, 1.0F);
                GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);
            }
            else if (te.getChestType() == BlockChest.Type.TRAP && wood != null)
            {
                bindTexture(TRAP_DOUBLE_TEXTURES.get(wood));
            }
            else if (wood != null)
            {
                bindTexture(DOUBLE_TEXTURES.get(wood));
            }
            else if (te.getChestType() == BlockChest.Type.TRAP && tree != null)
            {
                bindTexture(TRAP_DOUBLE_TEXTURES_TREE.get(tree));
            }
            else if (tree != null)
            {
                bindTexture(DOUBLE_TEXTURES_TREE.get(tree));
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0) GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

        GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        int rotation = 0;

        switch (meta)
        {
            case 2:
                rotation = 180;
                if (te.adjacentChestXPos != null) GlStateManager.translate(1.0F, 0.0F, 0.0F);
                break;
            case 3:
                rotation = 0;
                break;
            case 4:
                rotation = 90;
                break;
            case 5:
                rotation = -90;
                if (te.adjacentChestZPos != null) GlStateManager.translate(0.0F, 0.0F, -1.0F);
                break;
        }

        GlStateManager.rotate((float) rotation, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float lidAngle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

        if (te.adjacentChestZNeg != null)
        {
            float f1 = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;
            if (f1 > lidAngle) lidAngle = f1;
        }

        if (te.adjacentChestXNeg != null)
        {
            float f2 = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;
            if (f2 > lidAngle) lidAngle = f2;
        }

        lidAngle = 1.0F - lidAngle;
        lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
        modelchest.chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
        modelchest.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
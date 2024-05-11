package me.client.module.player;
/*
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.LeftClicker;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;*/
import me.client.module.Category;
import me.client.module.Module;
import me.client.module.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AutoTool extends Module {
    private Block previousBlock;
    private boolean isWaiting;
    public static int previousSlot;
    public static boolean justFinishedMining, mining;
    public static List<Class<?>> pickaxe = Arrays.asList(ItemBlock.class, BlockIce.class);

    public AutoTool() {
        super("Auto Tool", "Module", Category.PLAYER);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;

        if(!Mouse.isButtonDown(0)){
            if(mining)
                finishMining();
            if(isWaiting)
                isWaiting = false;
            return;
        }


        BlockPos lookingAtBlock = mc.objectMouseOver.getBlockPos();
        if (lookingAtBlock != null) {

            Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
            if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {


                    if(previousBlock != null){
                        if(previousBlock!=stateBlock){
                            previousBlock = stateBlock;
                            isWaiting = true;
                        } else {
                            if(isWaiting) {
                                isWaiting = false;
                                previousSlot = Utils.Player.getCurrentPlayerSlot();
                                mining = true;
                                hotkeyToFastest();
                            }
                        }
                    } else {
                        previousBlock = stateBlock;
                        isWaiting = false;
                    }
                    return;
                }

                if(!mining) {
                    previousSlot = Utils.Player.getCurrentPlayerSlot();
                    mining = true;
                }

                hotkeyToFastest();
            }
        }


    public void finishMining(){
        //      regresar al slot original
            Utils.Player.hotkeyToSlot(previousSlot);



            justFinishedMining = false;
        mining = false;
    }

    private void hotkeyToFastest(){
        int index = -1;
        double speed = 1;


        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot != null) {
                if( itemInSlot.getItem() instanceof ItemTool || itemInSlot.getItem() instanceof ItemShears){
                    BlockPos p = mc.objectMouseOver.getBlockPos();
                    Block bl = mc.theWorld.getBlockState(p).getBlock();

                    if(itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                        speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                        index = slot;
                    }
                }
            }
        }

        if(index == -1 || speed <= 1.1 || speed == 0) {
        } else {
            Utils.Player.hotkeyToSlot(index);
        }
    }
}

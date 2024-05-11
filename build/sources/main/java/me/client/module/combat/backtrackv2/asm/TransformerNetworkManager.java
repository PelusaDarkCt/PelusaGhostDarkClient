package me.client.module.combat.backtrackv2.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerNetworkManager implements Transformer {

    /*
    Injecting this code to channelRead0 function in NetworkManager

                        |
                        V

       if (me.mitfox.pingchanger.PingChanger.ShouldPacketBeSpoofed(p_channelRead0_2_)) {
           me.mitfox.pingchanger.PingChanger.SpoofPacket(p_channelRead0_2_);
           return;
        }
     */



    public String[] getClassName() {
        return new String[]{"net.minecraft.network.NetworkManager"};
    }
    public void transform(ClassNode classNode, String transformedName) {
        Iterator var3 = classNode.methods.iterator();
        while (var3.hasNext()) {
            MethodNode m = (MethodNode) var3.next();
            String n = this.mapMethodName(classNode, m);
            if (!n.equalsIgnoreCase("channelRead0")) continue;
            m.instructions.insert(m.instructions.getFirst(), this.getEventInsn());
        }
        return;
    }
    private InsnList getEventInsn() {
        InsnList toInject = new InsnList();
        toInject.add(new VarInsnNode(Opcodes.ALOAD, 2)); // load Packet onto stack
        toInject.add(new MethodInsnNode(INVOKESTATIC, "me/mitfox/pingchanger/PingChanger", "ShouldPacketBeSpoofed", "(Lnet/minecraft/network/Packet;)Z", false));
        LabelNode label = new LabelNode(new Label()); // create label for if statement
        toInject.add(new JumpInsnNode(IFEQ, label)); // jump to label if ShouldPacketBeSpoofed returns false
        toInject.add(new VarInsnNode(Opcodes.ALOAD, 2)); // load Packet onto stack again
        toInject.add(new MethodInsnNode(INVOKESTATIC, "me/mitfox/pingchanger/PingChanger", "SpoofPacket", "(Lnet/minecraft/network/Packet;)V", false));
        toInject.add(new InsnNode(Opcodes.RETURN)); // return from the method
        toInject.add(label); // mark the label
        toInject.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null)); // create a new frame
        return toInject;
    }
}
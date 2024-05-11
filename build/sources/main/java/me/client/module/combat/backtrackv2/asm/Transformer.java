package me.client.module.combat.backtrackv2.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public interface Transformer extends Opcodes {
   String[] getClassName();

   void transform(ClassNode classNode, String transformedName);

   default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, methodNode.name, methodNode.desc);
   }

   /**
    * Map the field name from notch names
    *
    * @param classNode the transformed class node
    * @param fieldNode the transformed classes field node
    * @return a mapped field name
    */
   default String mapFieldName(ClassNode classNode, FieldNode fieldNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(classNode.name, fieldNode.name, fieldNode.desc);
   }

   /**
    * Map the method desc from notch names
    *
    * @param methodNode the transformed method node
    * @return a mapped method desc
    */
   default String mapMethodDesc(MethodNode methodNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
   }

   /**
    * Map the method name from notch names
    *
    * @param methodInsnNode the transformed method insn node
    * @return a mapped insn method
    */
   default String mapMethodNameFromNode(MethodInsnNode methodInsnNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
   }

   /**
    * Map the field name from notch names
    *
    * @param fieldInsnNode the transformed field insn node
    * @return a mapped insn field
    */
   default String mapFieldNameFromNode(FieldInsnNode fieldInsnNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   default String mapClassName(String name) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapType(name);
   }

   /**
    * Remove instructions to this method
    *
    * @param methodNode the method being cleared
    */
   default void clearInstructions(MethodNode methodNode) {
      methodNode.instructions.clear();

      // dont waste time clearing local variables if they're empty
      if (!methodNode.localVariables.isEmpty()) {
         methodNode.localVariables.clear();
      }

      // dont waste time clearing try-catches if they're empty
      if (!methodNode.tryCatchBlocks.isEmpty()) {
         methodNode.tryCatchBlocks.clear();
      }
   }
}

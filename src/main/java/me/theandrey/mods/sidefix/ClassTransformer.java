package me.theandrey.mods.sidefix;

import java.util.ListIterator;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if (bytes != null) {
			if (name.equals("net.minecraftforge.fml.common.network.NetworkRegistry") ||
				name.equals("net.minecraftforge.fml.common.network.internal.FMLMessage")) {
				return redirectSideValues(bytes);
			}
		}

		return bytes;
	}

	private byte[] redirectSideValues(byte[] bytes) {
		ClassNode clazz = ASMHelper.readClass(bytes);
		Type sideType = ASMHelper.objectType("net.minecraftforge.fml.relauncher.Side");
		Type constantsType = ASMHelper.objectType("me.theandrey.mods.sidefix.Constants");
		boolean patched = false;

		for (MethodNode method : clazz.methods) {
			ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();

			while (iterator.hasNext()) {
				AbstractInsnNode node = iterator.next();

				if (node.getOpcode() == Opcodes.INVOKESTATIC) {
					MethodInsnNode methodNode = (MethodInsnNode)node;

					if (methodNode.owner.equals(sideType.getInternalName()) && methodNode.name.equals("values")) {
						// We can not modify Side class, so redirect values() call to constant.
						FieldInsnNode fieldNode = new FieldInsnNode(Opcodes.GETSTATIC, constantsType.getInternalName(), "SIDES", ASMHelper.arrayType(sideType).getDescriptor());
						iterator.set(fieldNode);
						patched = true;

						LoadingPlugin.LOGGER.debug("Replaced Side.values() call in {}#{}", clazz.name, method.name);
					}
				}
			}
		}

		if (patched) {
			return ASMHelper.writeClass(clazz, 0);
		}

		return bytes;
	}
}

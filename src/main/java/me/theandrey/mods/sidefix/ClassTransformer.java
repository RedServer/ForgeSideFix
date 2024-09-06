package me.theandrey.mods.sidefix;

import java.util.ListIterator;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if (bytes != null && name.equals("net.minecraftforge.fml.common.network.NetworkRegistry")) {
			return redirectSideValues(bytes);
		}

		return bytes;
	}

	private byte[] redirectSideValues(byte[] bytes) {
		ClassNode clazz = ASMHelper.readClass(bytes);
		boolean patched = false;

		for (MethodNode method : clazz.methods) {
			ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();

			while (iterator.hasNext()) {
				AbstractInsnNode node = iterator.next();

				if (node.getOpcode() == Opcodes.INVOKESTATIC) {
					MethodInsnNode methodNode = (MethodInsnNode)node;

					if (methodNode.owner.equals("net/minecraftforge/fml/relauncher/Side") && methodNode.name.equals("values")) {
						FieldInsnNode fieldNode = new FieldInsnNode(Opcodes.GETSTATIC, "me/theandrey/mods/sidefix/LoadingPlugin", "VALID_SIDES", "[Lnet/minecraftforge/fml/relauncher/Side;");
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

package me.theandrey.mods.sidefix;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public final class ASMHelper {

	private ASMHelper() {
	}

	public static ClassNode readClass(byte[] bytes) {
		ClassNode node = new ClassNode();
		new ClassReader(bytes).accept(node, 0);
		return node;
	}

	public static byte[] writeClass(ClassNode node, int flags) {
		ClassWriter writer = new ClassWriter(flags);
		node.accept(writer);
		return writer.toByteArray();
	}

	public static Type objectType(String name) {
		return Type.getObjectType(name.replace('.', '/'));
	}

	public static Type arrayType(Type type) {
		return Type.getType('[' + type.getDescriptor());
	}
}

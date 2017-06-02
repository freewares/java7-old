import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ExternalArrayData;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

class Rhino implements AutoCloseable {

	protected org.mozilla.javascript.Context cx;
	protected org.mozilla.javascript.Scriptable scope;

	protected void init(int version) {
		this.cx = org.mozilla.javascript.Context.enter();
		this.cx.setLanguageVersion(version);
		this.scope = cx.initStandardObjects();
	}

	public org.mozilla.javascript.Context context() {
		return this.cx;
	}

	public org.mozilla.javascript.Scriptable scope() {
		return this.scope;
	}

	public Rhino() {
		init(org.mozilla.javascript.Context.VERSION_1_2);
	}

	public Rhino(int version) {
		init(version);
	}

	@Override
	public void close() /* throws Exception */ {
		org.mozilla.javascript.Context.exit();
	}

	public Object evaluateString(String source, String sourceName, int lineno, Object securityDomain) {
		return this.cx.evaluateString(this.scope, source, sourceName, lineno, securityDomain);
	}

	public Object expr(String source) {
		return this.evaluateString(source, "<expr>", 1, null);
	}

	public ObjectProxy exprToObject(String source) {
		return new ObjectProxy(this.expr(source));
	}

	public static class ObjectProxy extends org.mozilla.javascript.NativeObject {
		protected org.mozilla.javascript.NativeObject m_obj = null;

		protected void init(org.mozilla.javascript.NativeObject o) {
			m_obj = o;
		}

		public ObjectProxy() {
			System.out.println("RhinoObject2(1)");
			init(new org.mozilla.javascript.NativeObject());
		}

		public ObjectProxy(org.mozilla.javascript.NativeObject o) {
			System.out.println("RhinoObject2(2)");
			init(o);
		}

		public ObjectProxy(ObjectProxy o) {
			System.out.println("RhinoObject2(3)");
			init(o.m_obj);
		}

		public ObjectProxy(Object o) {
			if (o instanceof ObjectProxy) {
				System.out.println("RhinoObject2(4)");
				init(((ObjectProxy) o).m_obj);
			} else if (o instanceof org.mozilla.javascript.NativeObject) {
				System.out.println("RhinoObject2(5)");
				init((org.mozilla.javascript.NativeObject) o);
			} else {
				System.out.println("RhinoObject2(6)");
				//// init(new org.mozilla.javascript.NativeObject());
				throw new RuntimeException();
			}
		}

		@Override
		public String getClassName() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getClassName();
		}

		@Override
		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.toString();
		}

		@Override
		public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
			// TODO 自動生成されたメソッド・スタブ
			if (thisObj == this)
				thisObj = m_obj;
			return m_obj.execIdCall(f, cx, scope, thisObj, args);
		}

		@Override
		public boolean containsKey(Object key) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.containsValue(value);
		}

		@Override
		public Object remove(Object key) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.remove(key);
		}

		@Override
		public Set<Object> keySet() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.keySet();
		}

		@Override
		public Collection<Object> values() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.values();
		}

		@Override
		public Set<Entry<Object, Object>> entrySet() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.entrySet();
		}

		@Override
		public Object put(Object key, Object value) {
			// TODO 自動生成されたメソッド・スタブ
			//// return m_obj.put(key, value);
			if (key instanceof Integer)
				m_obj.put((int) key, m_obj, value);
			else if (key instanceof String)
				m_obj.put((String) key, m_obj, value);
			return value;
		}

		@Override
		public void putAll(Map m) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.putAll(m);
		}

		@Override
		public void clear() {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.clear();
		}

		@Override
		public boolean has(String name, Scriptable start) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			return m_obj.has(name, start);
		}

		@Override
		public Object get(String name, Scriptable start) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			return m_obj.get(name, start);
		}

		@Override
		public void put(String name, Scriptable start, Object value) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			m_obj.put(name, start, value);
		}

		@Override
		public void delete(String name) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.delete(name);
		}

		@Override
		public int getAttributes(String name) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getAttributes(name);
		}

		@Override
		public void setAttributes(String name, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setAttributes(name, attributes);
		}

		@Override
		public void defineOwnProperty(Context cx, Object key, ScriptableObject desc) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineOwnProperty(cx, key, desc);
		}

		@Override
		public String getTypeOf() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getTypeOf();
		}

		@Override
		public boolean has(int index, Scriptable start) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			return m_obj.has(index, start);
		}

		@Override
		public Object get(int index, Scriptable start) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			return m_obj.get(index, start);
		}

		@Override
		public void put(int index, Scriptable start, Object value) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			m_obj.put(index, start, value);
		}

		@Override
		public void delete(int index) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.delete(index);
		}

		@Override
		public void putConst(String name, Scriptable start, Object value) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			m_obj.putConst(name, start, value);
		}

		@Override
		public void defineConst(String name, Scriptable start) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			m_obj.defineConst(name, start);
		}

		@Override
		public boolean isConst(String name) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.isConst(name);
		}

		@Override
		public void setAttributes(int index, Scriptable start, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			if (start == this)
				start = m_obj;
			m_obj.setAttributes(index, start, attributes);
		}

		@Override
		public int getAttributes(int index) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getAttributes(index);
		}

		@Override
		public void setAttributes(int index, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setAttributes(index, attributes);
		}

		@Override
		public void setGetterOrSetter(String name, int index, Callable getterOrSetter, boolean isSetter) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setGetterOrSetter(name, index, getterOrSetter, isSetter);
		}

		@Override
		public Object getGetterOrSetter(String name, int index, boolean isSetter) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getGetterOrSetter(name, index, isSetter);
		}

		@Override
		public void setExternalArrayData(ExternalArrayData array) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setExternalArrayData(array);
		}

		@Override
		public ExternalArrayData getExternalArrayData() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getExternalArrayData();
		}

		@Override
		public Object getExternalArrayLength() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getExternalArrayLength();
		}

		@Override
		public Scriptable getPrototype() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getPrototype();
		}

		@Override
		public void setPrototype(Scriptable m) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setPrototype(m);
		}

		@Override
		public Scriptable getParentScope() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getParentScope();
		}

		@Override
		public void setParentScope(Scriptable m) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.setParentScope(m);
		}

		@Override
		public Object[] getIds() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getIds();
		}

		@Override
		public Object[] getAllIds() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getAllIds();
		}

		@Override
		public Object getDefaultValue(Class<?> typeHint) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.getDefaultValue(typeHint);
		}

		@Override
		public boolean hasInstance(Scriptable instance) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.hasInstance(instance);
		}

		@Override
		public boolean avoidObjectDetection() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.avoidObjectDetection();
		}

		@Override
		public void defineProperty(String propertyName, Object value, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineProperty(propertyName, value, attributes);
		}

		@Override
		public void defineProperty(String propertyName, Class<?> clazz, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineProperty(propertyName, clazz, attributes);
		}

		@Override
		public void defineProperty(String propertyName, Object delegateTo, Method getter, Method setter,
				int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineProperty(propertyName, delegateTo, getter, setter, attributes);
		}

		@Override
		public void defineOwnProperties(Context cx, ScriptableObject props) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineOwnProperties(cx, props);
		}

		@Override
		public void defineFunctionProperties(String[] names, Class<?> clazz, int attributes) {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.defineFunctionProperties(names, clazz, attributes);
		}

		@Override
		public boolean isExtensible() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.isExtensible();
		}

		@Override
		public void preventExtensions() {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.preventExtensions();
		}

		@Override
		public synchronized void sealObject() {
			// TODO 自動生成されたメソッド・スタブ
			m_obj.sealObject();
		}

		@Override
		public int size() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.size();
		}

		@Override
		public boolean isEmpty() {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.isEmpty();
		}

		@Override
		public Object get(Object key) {
			// TODO 自動生成されたメソッド・スタブ
			return m_obj.get(key);
		}

	}

}

class RhinoArray extends org.mozilla.javascript.NativeArray {
	public RhinoArray() {
		super(0);
	}

	public RhinoArray(long lengthArg) {
		super(lengthArg);
	}

	@Override
	public boolean add(Object value) {
		super.put(this.size(), this, value);
		return true;
	}
}

class RhinoJSON {
	public static Object parse(org.mozilla.javascript.Context cx, org.mozilla.javascript.Scriptable scope, String jtext,
			org.mozilla.javascript.Callable reviver) {
		if (reviver != null)
			return org.mozilla.javascript.NativeJSON.parse(cx, scope, jtext, reviver);
		return org.mozilla.javascript.NativeJSON.parse(cx, scope, jtext, new org.mozilla.javascript.Callable() {
			@Override
			public Object call(org.mozilla.javascript.Context cx, org.mozilla.javascript.Scriptable scope,
					org.mozilla.javascript.Scriptable holdable, Object[] objects) {
				return objects[1];
			}
		});
	}

	public static Object stringify(org.mozilla.javascript.Context cx, org.mozilla.javascript.Scriptable scope,
			Object value, Object replacer, Object space) {
		return org.mozilla.javascript.NativeJSON.stringify(cx, scope, value, replacer, space);
	}

}

/**
 * Example of controlling the JavaScript execution engine.
 *
 * We evaluate a script and then manipulate the result.
 *
 */
@Slf4j
public class Control {

	/**
	 * Main entry point.
	 *
	 * Process arguments as would a normal Java program. Also create a new
	 * Context and associate it with the current thread. Then set up the
	 * execution environment and begin to execute scripts.
	 */
	public static void main(String[] args) {
		log.info("main(11)");
		Rhino engine = new Rhino();
		// org.mozilla.javascript.Context cx =
		// org.mozilla.javascript.Context.enter();
		try {
			// Set version to JavaScript1.2 so that we get object-literal style
			// printing instead of "[object Object]"
			// cx.setLanguageVersion(org.mozilla.javascript.Context.VERSION_1_2);

			// Initialize the standard objects (Object, Function, etc.)
			// This must be done before scripts can be executed.
			// org.mozilla.javascript.Scriptable scope =
			// cx.initStandardObjects();
			org.mozilla.javascript.Context cx = engine.context();

			org.mozilla.javascript.Scriptable scope = engine.scope();

			// Now we can evaluate a script. Let's create a new object
			// using the object literal notation.
			// Object result = cx.evaluateString(scope, "obj = {0:123, a:1,
			// b:['x','y']}", "MySource", 1, null);
			org.mozilla.javascript.Scriptable result = engine.exprToObject("obj = {0:123, a:1, b:['x','y']}");
			Object result2 = cx.evaluateString(scope, "obj2 = [1, 2, 3]", "MySource", 1, null);

			org.mozilla.javascript.Scriptable obj = (org.mozilla.javascript.Scriptable) scope.get("obj", scope);
			val obj2 = new Rhino.ObjectProxy(obj);
			obj2.put("mine", "aaa");
			obj2.put(0, "bbb");
			obj2.put(1, "ccc");
			System.out.println(obj.getClass().getName());
			// NativeObject nobj = (NativeObject) obj;
			// nobj.put("myprop", nobj, "myvalue2");
			// nobj.put("myprop", nobj, null);
			// nobj.put(111, nobj, null);
			// Object[] ids = nobj.getIds();
			// nobj.put("myprop2", nobj, new Date());
			// Scriptable obj2 = (Scriptable) scope.get("obj2", scope);
			// System.out.println(obj2.getClass().getName());
			// List<Object> ar = new ArrayList<>();
			// ar.add(1);
			// ar.add("x");
			RhinoArray na = new RhinoArray();
			// na.put(na.size(), na, 777);
			na.add(7777);
			na.add(999);
			obj2.put("ar", na);
			val obj3 = new Rhino.ObjectProxy(obj2);
			obj3.put("xyz", "789");
			// RhinoObject na2 = new RhinoObject(na);
			// obj2.put("ar2", na2);

			// Should print "obj == result" (Since the result of an assignment
			// expression is the value that was assigned)
			System.out.println("obj " + (obj == result ? "==" : "!=") + " result");

			// Should print "obj.a == 1"
			System.out.println("obj.a == " + obj.get("a", obj));

			org.mozilla.javascript.Scriptable b = (org.mozilla.javascript.Scriptable) obj.get("b", obj);

			// Should print "obj.b[0] == x"
			System.out.println("obj.b[0] == " + b.get(0, b));

			// Should print "obj.b[1] == y"
			System.out.println("obj.b[1] == " + b.get(1, b));

			// Should print {a:1, b:["x", "y"]}
			// Function fn = (Function) ScriptableObject.getProperty(obj,
			// "toString");
			// System.out.println(fn.call(cx, scope, obj, new Object[0]));

			Object json1 = RhinoJSON.stringify(cx, scope, obj, null, null);
			System.out.println(json1.getClass().getName());
			System.out.println(json1);
			// json1 = "123";
			Object x = RhinoJSON.parse(cx, scope, (String) json1, null);

			Object json2 = RhinoJSON.stringify(cx, scope, x, null, 2);
			System.out.println(json2.getClass().getName());
			System.out.println(json2);

		} finally {
			//// org.mozilla.javascript.Context.exit();
			engine.close();
		}
		/*
		 * ScriptEngineManager manager = new ScriptEngineManager(); for
		 * (ScriptEngineFactory factory : manager.getEngineFactories()) {
		 * System.out.println("Available language: " +
		 * factory.getLanguageName()); } ScriptEngine engine =
		 * manager.getEngineByName("js"); try { engine.eval("1+2"); } catch
		 * (ScriptException e) { // TODO 自動生成された catch ブロック e.printStackTrace();
		 * }
		 */
	}
}

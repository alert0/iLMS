package com.hanthink.mes.test.print.impl;

import java.io.IOException;
import java.util.logging.Level;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer; //import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;

public class EventLogListener {

	private static JISession configAndConnectDCom(String domain, String user,
			String pass) throws Exception {
		JISystem.getLogger().setLevel(Level.OFF);
		try {
			JISystem.setInBuiltLogHandler(false);
		} catch (IOException ignored) {
			;
		}
		JISystem.setAutoRegisteration(true);
		// JISession dcomSession = JISession.createSession(domain, user, pass);
		JISession dcomSession = JISession.createSession(); // 这个是直接用当前账号读，上面那行是用指定账号读
		dcomSession.useSessionSecurity(true);
		dcomSession.useNTLMv2(true);
		return dcomSession;
	}

	private static IJIDispatch getWmiLocator(String host, JISession dcomSession)
			throws Exception {
		// JIComServer wbemLocatorComObj = new JIComServer(JIProgId
		// .valueOf("WbemScripting.SWbemLocator"), host, dcomSession);
		JIComServer wbemLocatorComObj = new JIComServer(JIClsid
				.valueOf("76a64158-cb41-11d1-8b02-00600806d9b6"), host,
				dcomSession);
		return (IJIDispatch) JIObjectFactory.narrowObject(wbemLocatorComObj
				.createInstance().queryInterface(IJIDispatch.IID));
	}

	private static IJIDispatch toIDispatch(JIVariant comObjectAsVariant)
			throws JIException {
		return (IJIDispatch) JIObjectFactory.narrowObject(comObjectAsVariant
				.getObjectAsComObject());
	}

	private static String strJoin(JIVariant[] aArr, String sSep)
			throws JIException {
		StringBuilder sbStr = new StringBuilder();
		for (int i = 0, il = aArr.length; i < il; i++) {
			if (i > 0)
				sbStr.append(sSep);
			if (aArr[i].getType() == 8) {
				sbStr.append(aArr[i].getObjectAsString2());
			} else {
				sbStr.append(aArr[i].getObjectAsUnsigned().getValue());
			}
		}
		return sbStr.toString();
	}

	public static void main(String[] args) {
		// if (args.length != 4) {
		// System.out.println("Usage: "
		// + EventLogListener.class.getSimpleName()
		// + " domain host username password");
		// return;
		// }
		String domain = "xx.com";
		String host = "127.0.0.1";
		String user = "xxxx";
		String pass = "xxxxxx";
		JISession dcomSession = null;
		try {
			// Connect to DCOM on the remote system, and create an instance of
			// the WbemScripting.SWbemLocator object to talk to WMI.
			dcomSession = configAndConnectDCom(domain, user, pass);
			IJIDispatch wbemLocator = getWmiLocator(host, dcomSession);
			// Invoke the "ConnectServer" method on the SWbemLocator object via
			// it's IDispatch COM pointer. We will connect to
			// the default ROOT\CIMV2 namespace. This will result in us having a
			// reference to a "SWbemServices" object.
			JIVariant results[] = wbemLocator.callMethodA("ConnectServer",
					new Object[] { new JIString(host),
							new JIString("ROOT\\CIMV2"),
							JIVariant.OPTIONAL_PARAM(),
							JIVariant.OPTIONAL_PARAM(),
							JIVariant.OPTIONAL_PARAM(),
							JIVariant.OPTIONAL_PARAM(), new Integer(0),
							JIVariant.OPTIONAL_PARAM() });
			IJIDispatch wbemServices = toIDispatch(results[0]);
			// Now that we have a SWbemServices DCOM object reference, we
			// prepare a WMI Query Language (WQL) request to be informed
			// whenever a
			// new instance of the "Win32_NTLogEvent" WMI class is created on
			// the remote host. This is submitted to the remote host via the
			// "ExecNotificationQuery" method on SWbemServices. This gives us
			// all events as they come in. Refer to WQL documentation to
			// learn how to restrict the query if you want a narrower focus.
			final String QUERY_FOR_ALL_LOG_EVENTS = "SELECT * FROM __InstanceCreationEvent WHERE TargetInstance ISA 'Win32_NTLogEvent'";
			// final String QUERY_FOR_ALL_LOG_EVENTS =
			// "SELECT * FROM __InstanceCreationEvent WHERE TargetInstance ISA 'Win32_NTLogEvent' and TargetInstance.LogFile = 'System'";
			// Application
			// Security
			// System
			// and TargetInstance.LogFile = 'System'
			final int RETURN_IMMEDIATE = 16;
			final int FORWARD_ONLY = 32;
			JIVariant[] eventSourceSet = wbemServices.callMethodA(
					"ExecNotificationQuery", new Object[] {
							new JIString(QUERY_FOR_ALL_LOG_EVENTS),
							new JIString("WQL"),
							new JIVariant(new Integer(RETURN_IMMEDIATE
									+ FORWARD_ONLY)) });
			IJIDispatch wbemEventSource = (IJIDispatch) JIObjectFactory
					.narrowObject((eventSourceSet[0]).getObjectAsComObject());
			// The result of the query is a SWbemEventSource object. This object
			// exposes a method that we can call in a loop to retrieve the
			// next Windows Event Log entry whenever it is created. This
			// "NextEvent" operation will block until we are given an event.
			// Note that you can specify timeouts, see the Microsoft
			// documentation for more details.
			boolean flag = true;
			int i = 0;
			while (flag) {
				// this blocks until an event log entry appears.
				JIVariant eventAsVariant = (JIVariant) (wbemEventSource
						.callMethodA("NextEvent", new Object[] { JIVariant
								.OPTIONAL_PARAM() }))[0];
				IJIDispatch wbemEvent = toIDispatch(eventAsVariant);
				// WMI gives us events as SWbemObject instances (a base class of
				// any WMI object). We know in our case we asked for a specific
				// object
				// type, so we will Go ahead and invoke methods supported by
				// that Win32_NTLogEvent class via the wbemEvent IDispatch
				// pointer.
				// In this case, we simply call the "GetObjectText_" method that
				// returns us the entire object as a CIM formatted string. We
				// could,
				// however, ask the object for its property values via
				// wbemEvent.get("PropertyName"). See the j-interop
				// documentation and examples
				// for how to query COM properties.
				// JIVariant objTextAsVariant = (JIVariant)
				// (wbemEvent.callMethodA("GetObjectText_",
				// new Object[] { new Integer(1) }))[0];

				// String asText =
				// objTextAsVariant.getObjectAsString().getString();

				JIVariant variant2 = (JIVariant) (wbemEvent
						.get("TargetInstance"));
				IJIDispatch ddd = toIDispatch(variant2);
				// JIVariant objText = ddd.callMethodA("GetObjectText_", new
				// Object[] { new Integer(1) })[0];
				// String eventText = objText.getObjectAsString2();
				System.out
						.println("******************************************=="
								+ i++ + "==****************************");

				System.out.println("Category = "
						+ ddd.get("Category").getObjectAsInt());

				if (ddd.get("CategoryString").getType() == 8) {
					System.out.println("CategoryString = "
							+ ddd.get("CategoryString").getObjectAsString2());
				} else {
					System.out.println("CategoryString = "
							+ ddd.get("CategoryString").getObjectAsInt());
				}

				System.out.println("ComputerName = "
						+ ddd.get("ComputerName").getObjectAsString2());

				if (ddd.get("Data").getType() == 1) {
					System.out.println("Data = "
							+ ddd.get("Data").getObjectAsInt());
				} else {
					JIVariant[] array = (JIVariant[]) ddd.get("Data")
							.getObjectAsArray().getArrayInstance();
					String dataStr = strJoin(array, ", ");
					System.out.println("Data = " + dataStr);
				}

				System.out.println("EventCode = "
						+ ddd.get("EventCode").getObjectAsInt());
				System.out.println("EventIdentifier = "
						+ ddd.get("EventIdentifier").getObjectAsInt());
				System.out
						.println("EventType = "
								+ ddd.get("EventType").getObjectAsUnsigned()
										.getValue());

				JIVariant[] array1 = (JIVariant[]) ddd.get("InsertionStrings")
						.getObjectAsArray().getArrayInstance();

				String insertionStr = strJoin(array1, ", ");

				System.out.println("InsertionStrings = " + insertionStr);

				System.out.println("Logfile = "
						+ ddd.get("Logfile").getObjectAsString2());

				if (ddd.get("Message").getType() == 1) {
					System.out.println("Message = "
							+ ddd.get("Message").getObjectAsInt());
				} else {
					System.out.println("Message = "
							+ ddd.get("Message").getObjectAsString2());
				}

				System.out.println("RecordNumber = "
						+ ddd.get("RecordNumber").getObjectAsInt());
				System.out.println("SourceName = "
						+ ddd.get("SourceName").getObjectAsString2());
				System.out.println("TimeGenerated = "
						+ ddd.get("TimeGenerated").getObjectAsString2());
				System.out.println("TimeWritten = "
						+ ddd.get("TimeWritten").getObjectAsString2());
				System.out.println("Type = "
						+ ddd.get("Type").getObjectAsString2());
				if (ddd.get("User").getType() == 8) {
					System.out.println("User = "
							+ ddd.get("User").getObjectAsString2());
				} else {
					System.out.println("User = "
							+ ddd.get("User").getObjectAsInt());
				}

				// flag = false;

				// System.out.println("-----start------");
				// System.out.println(asText);
				// String[] texts = asText.split("\t");
				// for(int i = 0; i < texts.length; i++)
				// {
				// System.out.println( "texts["+ i +"]= "+texts[i]);
				// }

				// String props[] = asText.split("[\n\r(\r\n)\\n]");
				// Pattern p = Pattern.compile(
				// "^(\\s)*Message(\\s)*=(\\s)*\"?(.*)",
				// Pattern.CASE_INSENSITIVE);
				// Matcher m = null;
				// Pattern p2 = Pattern.compile("\\\\\"");
				// Matcher m2 = null;
				// for (String s : props) {
				// m = p.matcher(s);
				// if (m.matches()) {
				// m2 = p2.matcher(m.group(4));
				// System.out.println(m2.replaceAll(""));
				// }
				// }

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != dcomSession) {
				try {
					JISession.destroySession(dcomSession);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
package com.android.test.wifi;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.text.format.Formatter;
import android.util.Log;

/**
 * @author abc
 * 
 *         <pre>
 * ͨ�������鼮���ĵ�,��������, ��������һ����.
 * 
 * ��ʵ����WifiҲ�Ǻܼ򵥵ģ���Ҫʹ�����¼�������������
 * 
 * private WifiManager wifiManager;// �����������OpenWifi
 * private WifiInfo wifiInfo;// Wifi��Ϣ
 * private List&lt;ScanResult&gt; scanResultList; // ɨ����������������б�
 * private List&lt;WifiConfiguration&gt; wifiConfigList;// ���������б�
 * private WifiLock wifiLock;// Wifi��
 * 
 * 
 * ��Ȼ����Wifi������ģ�����н��У�����Ҫ�ŵ�����Wifi������Ͻ��У�
 * ���д���û�жԿ��ܴ��ڵĴ��������Ӧ�Ĳ����봦��ϣ���ο�������ע����һ�㣬
 * ����������ױ�ͻȻ�����Ĵ����󵼣�Ҳ�Ҳ������⣬���ڿ�����ʱ������������������⣡
 * ��˶Կ��ܴ��ڵ����⣬һ��Ҫ������Ӧ�Ĵ���
 * �����ǲ�����Щ����Ҫ��Ȩ�ޣ���Ȼ���ݲ��������ݲ�ͬ������Ȩ��Ҳ��ͬ��
 * �����Ȩ�޽����ο���
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 * <uses-permission android:name="adnroid.permission.ACCESS_CHECKIN_PROPERTTES"/>
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * <uses-permission android:name="android.permission.INTERNET"/>
 * <uses-permission android:name="adnroid.permission.CHANGE_WIFI_STATE"/>
 * <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>
 * </pre>
 */
public class WifiManageWrap {
	// �����������
	WifiManager mWifiManager;
	// Wifi��Ϣ
	private WifiInfo mWifiInfo;
	// ɨ����������������б�
	private List<ScanResult> scanResultList;
	// ���������б�
	private List<WifiConfiguration> wifiConfigList;
	// Wifi��
	private WifiLock mWifiLock;

	public WifiManageWrap(Context context) {
		// ��ȡWifi����
		this.mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// �õ�Wifi��Ϣ
		this.mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/** WIfi�Ƿ��Ѿ����� */
	public boolean isWifiEnabled() {
		return mWifiManager.isWifiEnabled();
	}

	/** ��wifi */
	public boolean openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			return mWifiManager.setWifiEnabled(true);
		} else {
			return false;
		}
	}

	/** �ر� wifi */
	public boolean closeWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			return true;
		} else {
			return mWifiManager.setWifiEnabled(false);
		}
	}

	// ��ʵ����WiFI�����ж�wifi�Ƿ����ɹ���������ʹ�õ���held�����ֵ���˼acquire �õ���
	/** ����wifi */
	public void lockWifi() {
		mWifiLock.acquire();
	}

	/** ����wifi */
	public void unLockWifi() {
		if (!mWifiLock.isHeld()) {
			// �ͷ���Դ
			mWifiLock.release();
		}
	}

	/** Wifi �� */
	public WifiLock createWifiLock() {
		if (mWifiLock == null) {
			// ����һ�����ı�־
			mWifiLock = mWifiManager.createWifiLock("flyfly");
		}
		return mWifiLock;
	}

	/** ɨ������ */
	public void startScan() {
		mWifiManager.startScan();
		// ɨ�践�ؽ���б�
		scanResultList = mWifiManager.getScanResults();
		// ɨ�������б�
		wifiConfigList = mWifiManager.getConfiguredNetworks();
	}

	/** ScanResult List */
	public List<ScanResult> getWifiList() {
		return scanResultList;
	}

	/** WifiConfiguration list */
	public List<WifiConfiguration> getWifiConfigList() {
		return wifiConfigList;
	}

	/** ��ȡָ���źŵ�ǿ�� */
	public int getLevel(int NetId) {
		return scanResultList.get(NetId).level;
	}

	/** ��ȡ����Mac��ַ */
	public String getMac() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getMacAddress();
	}

	/** ��ȡBSSID */
	public String getBSSID() {
		return (mWifiInfo == null) ? null : mWifiInfo.getBSSID();
	}

	public String getSSID() {
		return (mWifiInfo == null) ? null : mWifiInfo.getSSID();
	}

	/** ���ص�ǰ���ӵ������ID */
	public int getCurrentNetId() {
		return (mWifiInfo == null) ? null : mWifiInfo.getNetworkId();
	}

	/** ����WifiInfo��Ϣ */
	public String getwifiInfo() {
		return (mWifiInfo == null) ? null : mWifiInfo.toString();
	}

	/** ��ȡIP��ַ */
	public int getIP() {
		return (mWifiInfo == null) ? null : mWifiInfo.getIpAddress();
	}

	/** ���һ������ */
	public boolean addNetWordLink(WifiConfiguration config) {
		Log.v("V", "start add WifiConfiguration");
		int NetId = mWifiManager.addNetwork(config);
		Log.v("V", "start save WifiConfiguration.." + NetId);
		return mWifiManager.saveConfiguration();
		// return wifiManager.enableNetwork(NetId, true);
	}

	/** ����һ������ */
	public boolean disableNetWordLick(int NetId) {
		mWifiManager.disableNetwork(NetId);
		return mWifiManager.disconnect();
	}

	/** �Ƴ�һ������ */
	public boolean removeNetworkLink(int NetId) {
		return mWifiManager.removeNetwork(NetId);
	}

	/** ����ʾSSID */
	public void hiddenSSID(int NetId) {
		wifiConfigList.get(NetId).hiddenSSID = true;
	}

	/** ��ʾSSID */
	public void displaySSID(int NetId) {
		wifiConfigList.get(NetId).hiddenSSID = false;
	}

	/** wifiConfigList ȥָ����WifiConfiguration */
	public WifiConfiguration getWifiConfig(String ssid) {
		WifiConfiguration reWifiConfiguration = null;
		wifiConfigList = mWifiManager.getConfiguredNetworks();
		String findSsid = new StringBuilder().append("\"").append(ssid)
				.append("\"").toString();
		if (wifiConfigList != null) {
			for (WifiConfiguration conf : wifiConfigList) {
				if (conf.SSID.equals(findSsid)) {
					reWifiConfiguration = conf;
					break;
				}
			}
		}
		return reWifiConfiguration;
	}

	/** WifiConfiguration���� */
	public static WifiConfiguration copyWifiConfiguration(
			WifiConfiguration srcWifiConfiguration) {
		WifiConfiguration newWifiConfiguration = new WifiConfiguration();
		if (srcWifiConfiguration != null) {
			newWifiConfiguration.SSID = "new" + srcWifiConfiguration.SSID;
			newWifiConfiguration.preSharedKey = srcWifiConfiguration.preSharedKey;
			newWifiConfiguration.hiddenSSID = srcWifiConfiguration.hiddenSSID;
			newWifiConfiguration.networkId = srcWifiConfiguration.networkId;
			newWifiConfiguration.priority = srcWifiConfiguration.priority;
			newWifiConfiguration.BSSID = srcWifiConfiguration.BSSID;
			newWifiConfiguration.status = srcWifiConfiguration.status;
			newWifiConfiguration.wepKeys = srcWifiConfiguration.wepKeys;
			newWifiConfiguration.wepTxKeyIndex = srcWifiConfiguration.wepTxKeyIndex;

			newWifiConfiguration.allowedAuthAlgorithms = srcWifiConfiguration.allowedAuthAlgorithms;
			newWifiConfiguration.allowedGroupCiphers = srcWifiConfiguration.allowedGroupCiphers;
			newWifiConfiguration.allowedKeyManagement = srcWifiConfiguration.allowedKeyManagement;
			newWifiConfiguration.allowedPairwiseCiphers = srcWifiConfiguration.allowedPairwiseCiphers;
			newWifiConfiguration.allowedProtocols = srcWifiConfiguration.allowedProtocols;
		}
		return newWifiConfiguration;
	}

	/** ��ȡɨ���б� */
	public String scanResultListToString() {
		scanResultList.size();
		if (scanResultList == null) {
			return null;
		}
		StringBuffer scanBuilder = new StringBuffer();
		int i = 0;
		for (ScanResult sr : scanResultList) {
			scanBuilder.append(i);
			scanBuilder.append('\n');
			// ������Ϣ
			scanBuilder.append(scanResultList.get(i).toString());
			scanBuilder.append('\n');
		}
		return scanBuilder.toString();
	}

	/** wifiConfigList��Ϣ */
	public String wifiConfigurationListToString() {
		wifiConfigList = mWifiManager.getConfiguredNetworks();
		if (wifiConfigList == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("wifiConfigList-");
		sb.append(wifiConfigList.size());
		sb.append('\n');
		int i = 0;
		for (WifiConfiguration wifiConfig : wifiConfigList) {
			sb.append(i);
			sb.append('\n');
			sb.append(wifiConfigurationToString(wifiConfig));
			sb.append('\n');
		}
		return sb.toString();
	}

	public String wifiConfigurationToString(
			WifiConfiguration srcWifiConfiguration) {
		if (srcWifiConfiguration == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SSID=");
		sb.append(srcWifiConfiguration.SSID);
		sb.append('\n');
		sb.append("preSharedKey=");
		sb.append(srcWifiConfiguration.preSharedKey);
		sb.append('\n');
		sb.append("hiddenSSID=");
		sb.append(srcWifiConfiguration.hiddenSSID);
		sb.append('\n');
		sb.append("networkId=");
		sb.append(srcWifiConfiguration.networkId);
		sb.append('\n');
		sb.append("priority=");
		sb.append(srcWifiConfiguration.priority);
		sb.append('\n');
		sb.append("BSSID=");
		sb.append(srcWifiConfiguration.BSSID);
		sb.append('\n');
		sb.append("status=");
		sb.append(srcWifiConfiguration.status);
		sb.append('\n');
		sb.append("wepKeys=");
		stringArrayToString(sb, srcWifiConfiguration.wepKeys);
		sb.append('\n');
		sb.append("wepTxKeyIndex=");
		sb.append(srcWifiConfiguration.wepTxKeyIndex);
		sb.append('\n');

		sb.append("allowedAuthAlgorithms=");
		sb.append(srcWifiConfiguration.allowedAuthAlgorithms);
		sb.append('\n');
		sb.append("allowedGroupCiphers=");
		sb.append(srcWifiConfiguration.allowedGroupCiphers);
		sb.append('\n');
		sb.append("allowedKeyManagement=");
		sb.append(srcWifiConfiguration.allowedKeyManagement);
		sb.append('\n');
		sb.append("allowedPairwiseCiphers=");
		sb.append(srcWifiConfiguration.allowedPairwiseCiphers);
		sb.append('\n');
		sb.append("allowedProtocols=");
		sb.append(srcWifiConfiguration.allowedProtocols);
		sb.append('\n');
		return sb.toString();
	}

	private static void stringArrayToString(StringBuilder sb, String[] strs) {
		sb.append('[');
		if (strs != null) {
			for (String str : strs) {
				sb.append(str);
				sb.append(',');
			}
		}
		sb.append(']');
	}

	@SuppressLint("NewApi")
	public String wifiInfoToString() {
		if (mWifiInfo == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SSID��").append(mWifiInfo.getSSID()).append('\n');
		sb.append("BSSID").append(mWifiInfo.getBSSID()).append('\n');
		sb.append("describeContents��").append(mWifiInfo.describeContents())
				.append('\n');
		sb.append("HiddenSSID��").append(mWifiInfo.getHiddenSSID()).append('\n');
		sb.append("IpAddress��")
				.append(Formatter.formatIpAddress(mWifiInfo.getIpAddress()))
				.append('\n');
		sb.append("LinkSpeed��").append(mWifiInfo.getLinkSpeed()).append('\n');
		sb.append("MacAddress��").append(mWifiInfo.getMacAddress()).append('\n');
		sb.append("NetworkId��").append(mWifiInfo.getNetworkId()).append('\n');
		sb.append("Rssi��").append(mWifiInfo.getRssi()).append('\n');
		sb.append("SupplicantState��").append(mWifiInfo.getSupplicantState())
				.append('\n');
		return sb.toString();
	}
}

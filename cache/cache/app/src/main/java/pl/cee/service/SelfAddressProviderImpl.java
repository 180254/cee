package pl.cee.service;

import org.springframework.core.env.Environment;

import java.net.*;
import java.util.Enumeration;

public class SelfAddressProviderImpl implements SelfAddressProvider {

    private final String selfAddress;

    public SelfAddressProviderImpl(Environment env) throws SocketException {
        StringBuilder selfAddress = new StringBuilder(256);

        String serverPort = env.getProperty("server.port", "8080");
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress address = interfaceAddress.getAddress();

                if (address instanceof Inet4Address) {
                    String hostAddress = address.getHostAddress();
                    selfAddress.append(hostAddress).append(":").append(serverPort).append("; ");
                }
            }
        }

        this.selfAddress = selfAddress.toString().trim();
    }

    @Override
    public String getSelfAddress() {
        return selfAddress;
    }
}

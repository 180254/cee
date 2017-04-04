package pl.cee.service;

import org.springframework.core.env.Environment;

import java.net.*;
import java.util.Enumeration;

public class SelfAddressProviderImpl implements SelfAddressProvider {

    private final String selfAddress;

    public SelfAddressProviderImpl(Environment env) throws SocketException {
        StringBuilder selfAddress = new StringBuilder(100);

        String serverPort = env.getProperty("server.port");

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress address = interfaceAddress.getAddress();

                if (address instanceof Inet4Address) {
                    if (selfAddress.length() != 0) {
                        selfAddress.append("; ");
                    }

                    String hostAddress = address.getHostAddress();
                    selfAddress.append(hostAddress).append(":").append(serverPort);
                }
            }
        }

        this.selfAddress = selfAddress.toString();
    }

    @Override
    public String getSelfAddress() {
        return selfAddress;
    }
}

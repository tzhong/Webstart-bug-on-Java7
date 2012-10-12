This is the sample application to repro a webstart bug on Java 7.
ClassLoader.getResource(name) always returns null for a nativelib
embedded in JNLP.
The workaround is to change the nativelib to a normal jar resource.

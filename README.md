# NfcActivity
An Android library that makes NFC communication Simple.

#### whats new in 1.2?
* Added `writeUri` to `NfcConnection` - used to write URI records to NFC tags.

#### whats new in 1.1?
* Supports `AppCompatActivity` - optional `NfcAppCompatActivity` and `BeamAppCompatActivity`.
* New `NfcBeamActivity` (and `NfcBeamAppCompatActivity`) - your Activities can now implement Nfc and Android Beam.

Usage
--------
1) extend `NfcActivity`:

  ```java
  public class YourActivity extends NfcActivity
  ```
2) implement `OnNfcStart(NfcConnection conn)`:

  ```java
  @Override
  public void onNfcStart(NfcConnection conn)
  {
      ...
  }
  ```
3) Done! now you can use `NfcConnection` methods to manipulate NFC tags.
    
  ```java
  String id = conn.getTagId(); // get unique tag id
  String data = conn.read(); // get string data from tag
  conn.write("Hello, World!"); // write string data to tag
  conn.write("Hello, World!", this); // write string data to tag + AAR
  conn.writeUri("https://example.com"); // from 1.2: tag will open the browser at example.com
  conn.makeReadOnly(); // make tag "read only"
  ```

  Notice you will need to use try-catch to catch thrown exceptions: 
  `NfcDisconnectedException`, `NfcNotWritableException`, `NfcTextTooLargeException`, `IOException` and `FormatException`.

  see full usage example on [GitHub][1].

  Some other included features:
  * BeamActivity - used like NfcActivity, to implement 'Android Beam'.
  * NfcTester object - used it's methods to check the device's NFC state.

Download
--------
via Gradle:
```groovy
compile 'com.rgghgh.nfcactivity:nfcactivity:1.2'
```
or Maven:
```xml
<dependency>
  <groupId>com.rgghgh.nfcactivity</groupId>
  <artifactId>nfcactivity</artifactId>
  <version>1.2</version>
  <type>pom</type>
</dependency>
```

License
--------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: https://github.com/Rgghgh/NfcActivity/blob/master/app/src/main/java/com/rgghgh/nfcactivitydemo/MainActivity.java#L13

# Cloudflare Gateway Log Collector for Splunk Data Inputs
A Splunk extension to collect logs for Cloudflare Gateway Activities(Splunk Data Inputs).

This is unstable. It may be changed at any time. Use it only for evaluation purposes.

Currently, It does not support Cloudflare Gateway DNS and Network. But, it was written in consideration of extensions for them.

![An example with Splunk Enterprise Sankey Diagram](https://raw.githubusercontent.com/donghoon-yoo/splunk-cfgw-collector/main/.resources/spl.webp)

## Requirements
- [Splunk Enterprise Indexer](https://docs.splunk.com/Documentation/Splunk/latest/Capacity/ComponentsofaSplunkEnterprisedeployment) 9.+
- A Cloudflare Zero Trust Organization, including **Enterprise Bundles**

## Build
### Prerequisite
- Kotlin Multiplatform 1.9.0
- Gradle 8.3

### Command
- **For macOS arm64**
    ```shell
    ./gradlew macosArm64Binaries
    ```
- **For macOS x64**
    ```shell
    ./gradlew macosX64Binaries
    ```
- **For Linux arm64**
    ```shell
    ./gradlew linuxArm64Binaries
    ```
- **For Linux x64**
    ```shell
    ./gradlew linuxX64Binaries
    ```
- **For Windows x64**
    ```shell
    ./gradlew mingwX64Binaries
    ```

## Register a data inputs to Splunk Enterprise
To register a data inputs to Splunk, Add settings on Splunk Web with interactive web interface or define files as following, and then restart `splunkd` service.

- %SPLUNK_HOME%\etc\apps\search\local\inputs.conf
    ```text
    [powershell://Cloudflare-Gateway]
    disabled = 0
    index = cfgw_analytics
    interval = 1
    script = . "$SplunkHome\etc\apps\search\bin\splunk-cloudflare-gateway.ps1"
    sourcetype = cfgw_analytics_http
    schedule = * * * * *
    ```
- %SPLUNK_HOME%\etc\apps\search\bin\splunk-cloudflare-gateway.ps1
    ```powershell
    $env:RVL_TOKEN = 'Token goes here';
    $env:RVL_ZONEID = 'Zone ID goes here';
    
    & "$SplunkHome\etc\apps\search\bin\splunk-cloudflare-gateway.exe"
    ```

## Environment variables
- Int, Long: Parsed from string. Do not use the thousand commas.
- Boolean: Parsed as follows - 0 is false, 1 is true.

| Name                | Type       | Description                                     | Required | Default                    |
|---------------------|------------|-------------------------------------------------|----------|----------------------------|
| RVL_ENDPOINT        | String     | Cloudflare API Endpoint without trailing slash  | Yes      | https://api.cloudflare.com |
| **RVL_TOKEN**       | **String** | **Cloudflare API Bearer Token**                 | **Yes**  | **-**                      |
| **RVL_ZONEID**      | **String** | **Cloudflare Zero Trust Organization ID**       | **Yes**  | **-**                      |
| RVL_TIMEOUTMILLIS   | Long       | (HTTP Client)Timeout in milliseconds            | Yes      | 25000                      |
| RVL_RETRIES         | Int        | (HTTP Client)Maximum amount of retries          | Yes      | 3                          |
| RVL_NORMALIZE       | Boolean    | Normalize datetime in minute by deleting second | Yes      | 1                          |
| RVL_DURATIONMINUTES | Int        | See 'Time processing' section.                  | Yes      | 1                          |
| RVL_OFFSETMINUTES   | Int        | See 'Time processing' section.                  | Yes      | 1                          |

## Time processing
It fetches data for the last few minutes each time it runs. 
Thus, `RVL_DURATIONMINUTES` and `RVL_OFFSETMINUTE` have the following meanings.
The reason why there are parameters such as normalize and offset is that the time when the job starts is not consistent within about 1~2 seconds by Splunk Data Inputs. This prevents data from being collected in duplicate.

```text
                   |-Duration-|--Offset--|

        |----------|----------|----------| 
Time: 00:00      01:00      02:00       now
```

- `RVL_DURATIONMINUTES`: Specify data collection period 
- `RVL_OFFSETMINUTE`: Specify offset value for end of timerange from the time job started.

## Contributing
Currently, There are no specified contribution rules because the source tree is small enough to be easily tracked.

---

**(C) 2023 DongHoon Yoo.**

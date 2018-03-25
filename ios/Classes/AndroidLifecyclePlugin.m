#import "AndroidLifecyclePlugin.h"
#import <android_lifecycle/android_lifecycle-Swift.h>

@implementation AndroidLifecyclePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAndroidLifecyclePlugin registerWithRegistrar:registrar];
}
@end

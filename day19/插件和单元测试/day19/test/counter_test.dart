import 'package:flutter_test/flutter_test.dart';
import 'package:day19/counter.dart';

/// 也可以使用命令来运行 flutter test test/counter_test.dart

void main() {
  // 单一的测试
  test("测试 value 递增", () {
    final counter = Counter();
    counter.increment();

    // 验证 counter.value 的是是否为 1
    expect(counter.value, 1);
  });
}
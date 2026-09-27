package com.epam.codereviewagent.antipattern;

import java.util.*;

public class SampleAntipatterns {

  public List<Double> history = new ArrayList<>();

  private ArrayList results = new ArrayList();

  static class CodeMathHelper {
    double square(double n) {
      return n * n;
    }
  }

  public double calculateStatistics(double a, double b, double c, double d, double e, double f, double g) {

    double sum = a + b + c + d + e + f + g;
    double average = sum / 7;
    double scaled = average * 3.14159;

    if (average > 0) {
      if (scaled > 100) {
        if (scaled < 1000) {
          if (scaled % 2 == 0) {
            results.add(scaled);
          }
        }
      }
    }

    return scaled;
  }

  public double divide(double numerator, double denominator) {
    double result = 0;
    try {
      result = numerator / checkNotZero(denominator);
    } catch (Exception e) {
      // ignored
    }
    return result;
  }

  private double checkNotZero(double value) throws Exception {
    if (value == 0) {
      throw new Exception("division by zero");
    }
    return value;
  }

  public Double average(List<Double> values) {
    if (values.isEmpty()) {
      return null;
    }
    double sum = 0;
    for (double value : values) {
      sum += value;
    }
    return sum / values.size();
  }

  public boolean prime(int n) {
    if (n < 2) {
      return false;
    }
    for (int i = 2; i < n; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }


  public String buildReport(List<Double> values) {
    String report = "";
    for (double value : values) {
      report += value + "\n";
    }
    return report;
  }
}
